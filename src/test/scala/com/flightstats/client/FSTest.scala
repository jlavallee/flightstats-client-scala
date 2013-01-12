package com.flightstats.client

import dispatch.Promise
import com.ning.http.client.RequestBuilder
import com.ning.http.client.FluentStringsMap
import java.io.{File => JFile, PrintWriter => JPrintWriter}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.DeserializationFeature


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
}

class FSTestClients { }

trait FSTest {
  val appId = sys.props.get("flightstats.appid")
  val appKey = sys.props.get("flightstats.appkey")

  def debug(promise: Promise[Either[Throwable, AnyRef]]) {
    if(appId.isDefined && appKey.isDefined)
      println(promise) // it's cool to see that we're really async when we test w/creds
  }
}

trait FSTestRun extends FSClientBase
    with FSStaticTestJson
    with FSClientReboot {
  val capture = sys.props.get("test.capture")

  override def extendedOptions = Seq("testRun")

  override def getWithCreds(url: RequestBuilder) : Promise[String] = {
    println("URL: " + url.build().getRawUrl())
    for (x <- super.getWithCreds(url)) yield {
      capture match {
        case Some(v) if v.toBoolean => capture(url, x)
        case _ => x
      }
    }
  }

  // write the string to a file & return it
  // this is pretty awful
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
  override def mapFromJson[T](t: Class[T], json: String): T =
    StrictJacksonMapper.mapper.readValue(json, t)
}

private object StrictJacksonMapper {
  def mapper = {
    val m = JacksonMapper.mapper
    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    m
  }
}