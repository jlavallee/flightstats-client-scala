package com.mobilerq.flightstats.client

import scala.concurrent.duration.Duration
import scala.concurrent.{Future, ExecutionContext}
import org.junit.Assert._
import com.ning.http.client.{FluentStringsMap, RequestBuilder}
import java.io.{File => JFile, PrintWriter => JPrintWriter}
import java.util.concurrent.{Executors => JExecutors}
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import scala.reflect.runtime.universe._
import scala.reflect.runtime.{currentMirror => mirror}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.datatype.joda.JodaModule
import scala.concurrent.duration.Duration
import scala.concurrent.{Future, ExecutionContext}


object FSTest {
  val executor = ExecutionContext.fromExecutor(JExecutors.newSingleThreadExecutor())
}
trait FSTest {
  val appId = sys.props.get("flightstats.appId")
  val appKey = sys.props.get("flightstats.appKey")
  val duration = Duration("10 seconds")

  // need to execute exerciseCaseClass in a single thread, reflection isn't threadsafe
  implicit val executor = FSTest.executor

  def debug(promise: Future[AnyRef]) {
    if(appId.isDefined && appKey.isDefined)
      println(promise) // it's cool to see that we're really async when we test w/creds
  }

  def exerciseCaseClass(foo: AnyRef) {
    val im = mirror.reflect(foo)

    if(!im.symbol.isCaseClass) return

    val accessors = im.symbol.asClass.typeSignature.declarations.collect {
      case m: MethodSymbol if m.isCaseAccessor => m
    }

    accessors.foreach( checkNotNull(im, _))
  }

  def checkNotNull(im: InstanceMirror, m: MethodSymbol) {
    val ca = im.reflectMethod(m.asMethod)
    val r = ca()

    assertNotNull(im.symbol.name + "." + m.name + " = " + r, r)

    r match {
      case None => Unit
      case Some(null) => fail("found Some(null)")
      case null => fail(im.symbol.name + "." + m.name + " = null")
      case Some(i: AnyRef) => exerciseCaseClass(i)
      case x:AnyRef => exerciseCaseClass(x)
      case y => fail("expected at least an AnyRef, got: " + y)
    }
  }
}

object FSTestClients extends FSTest {
  val testAppId = "testId"
  val testAppKey = "testKey"

  def airports: FSAirports = (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSAirports(id, key) with FSTestRun
      case (_, _) =>
        new FSAirports(testAppId, testAppKey) with FSMockClient with StrictJacksonMapper
    }

  def alerts: FSAlerts =
    (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSAlerts(id, key) with FSTestRun
      case (_, _) =>
        new FSAlerts(testAppId, testAppKey) with FSMockClient with StrictJacksonMapper
    }

  def delayIndexes: FSDelayIndexes =
    (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSDelayIndexes(id, key) with FSTestRun
      case (_, _) =>
        new FSDelayIndexes(testAppId, testAppKey) with FSMockClient with StrictJacksonMapper
    }

  def flightStatusByFlight: FSFlightStatusByFlight =
    (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSFlightStatusByFlight(id, key) with FSTestRun
      case (_, _) =>
        new FSFlightStatusByFlight(testAppId, testAppKey) with FSMockClient
    }

  def flightStatusByAirport: FSFlightStatusByAirport =
    (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSFlightStatusByAirport(id, key) with FSTestRun
      case (_, _) =>
        new FSFlightStatusByAirport(testAppId, testAppKey) with FSMockClient
    }

  def flightStatusByRoute: FSFlightStatusByRoute =
    (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSFlightStatusByRoute(id, key) with FSTestRun
      case (_, _) =>
        new FSFlightStatusByRoute(testAppId, testAppKey) with FSMockClient
    }

  def flightsNear: FSFlightsNear =
    (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSFlightsNear(id, key) with FSTestRun
      case (_, _) =>
        new FSFlightsNear(testAppId, testAppKey) with FSMockClient
    }

  def ratings: FSRatings =
    (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSRatings(id, key) with FSTestRun
      case (_, _) =>
        new FSRatings(testAppId, testAppKey) with FSMockClient
    }

  def weather: FSWeather =
    (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSWeather(id, key) with FSTestRun
      case (_, _) =>
        new FSWeather(testAppId, testAppKey) with FSMockClient
    }

  def connections: FSConnections =
    (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSConnections(id, key) with FSTestRun
      case (_, _) =>
        new FSConnections(testAppId, testAppKey) with FSMockClient
    }

}

class FSTestClients { }

trait FSTestRun extends FSClientBase
    with FSStaticTestJson
    with FSClientReboot {
  val capture = sys.props.get("test.capture")

  override protected def getWithCreds(url: RequestBuilder) : Future[String] = {
    import ExecutionContext.Implicits.global
    println("URL: " + url.build().getRawUrl())
    for (x <- super.getWithCreds(url)) yield {
      capture match {
        case Some(v) if v.toBoolean => capture(url, x)
        case _ => x
      }
    }
  }

  // Write the string to a file & return it.  This is pretty awful.
  def capture(url: RequestBuilder, contents: String): String = {
    val filePath = filePathForUrl(url)
    val dirPath = filePath.substring(0, filePath.lastIndexOf("/"))
    (new JFile(dirPath)).mkdirs()

    val out = new JPrintWriter( new JFile(filePath), "UTF-8" )
    try { out.print( contents ) }
    finally { out.close }

    contents
  }
}

trait FSStaticTestJson {
  private val startDir = "src/test/resources/"
  private def urlPath2filePath(path: String): String =
    path.split('/').mkString(JFile.separator)

  def filePathForUrl(url: RequestBuilder): String = {
    val queryPathWithHost =  url.build().getUrl().split("://")(1) // yuck!
    val queryPathWithoutHost = queryPathWithHost.substring(queryPathWithHost.indexOf("/"))
    urlPath2filePath(startDir + queryPathWithoutHost) + ".json"
  }
}

trait StrictJacksonMapper extends FSClient {
  override protected def mapFromJson[T](t: Class[T], json: String): T =
    StrictJacksonMapper.mapper.readValue(json, t)
}

private object StrictJacksonMapper {
  val mapper = newMapper
  def newMapper = {
    val m = new ObjectMapper()
    m.registerModule(DefaultScalaModule)
    m.registerModule(new JodaModule)
    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
  }
}