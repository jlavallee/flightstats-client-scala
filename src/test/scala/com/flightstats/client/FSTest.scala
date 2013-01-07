package com.flightstats.client

import org.scalatest.Assertions
import dispatch.Promise
import com.ning.http.client.RequestBuilder


object FSTestClients extends FSTest {
  def airports: FSAirports = (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSAirports(id, key) with FSTestRun
      case (_, _) =>
        new FSAirports("mockId", "mockKey") with FSMockClient
    }
  
  def alerts: FSAlerts =
    (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSAlerts(id, key) with FSTestRun
      case (_, _) =>
        new FSAlerts("mockId", "mockKey") with FSMockClient
    }
}

class FSTestClients {
  
}

trait FSTest extends Assertions {
  val appId = sys.props.get("flightstats.appid")
  val appKey = sys.props.get("flightstats.appkey")

  def debug(promise: Promise[Either[Throwable, AnyRef]]) {
    if(appId.isDefined && appKey.isDefined)
      println(promise) // it's cool to see that we're really async when we test w/creds
  }
}

trait FSTestRun extends FSClient
    with FSStaticTestJson
    with FSClientReboot
    with JacksonMapper {
  val capture = sys.props.get("test.capture")

  override def extendedOptions = Seq("testRun")

  override def getWithCreds(url: RequestBuilder) : Promise[Either[Throwable, String]] = {
    println("URL: " + url.build().getRawUrl())
    for (x <- super.getWithCreds(url).right) yield {
      capture match {
        case Some(v) if v.toBoolean => capture(url, x)
        case _ => x
      }
    }
  }

  // write the string to a file & return it
  // this is pretty awful
  def capture(url: RequestBuilder, contents: String): String = {
    import java.io.{File, PrintWriter}
    val filePath = filePathForUrl(url)
    val dirPath = filePath.substring(0, filePath.lastIndexOf("/"))
    (new File(dirPath)).mkdirs()
    
    val out = new PrintWriter( new File(filePath) )
    try { out.print( contents ) }
    finally { out.close }

    contents
  }
}

trait FSStaticTestJson {
  val startDir = "src/test/resources/"
  def filePathForUrl(url: RequestBuilder): String = {
    val queryPathWithHost =  url.build().getRawUrl().split("://")(1)
    val queryPathWithoutHost = queryPathWithHost.substring(queryPathWithHost.indexOf("/"))
    val queryPathWithoutQuery = queryPathWithoutHost.indexOf("?") match {
      case -1 => queryPathWithoutHost
      case  x => queryPathWithoutHost.substring(0, x)
    }
    return startDir + queryPathWithoutQuery + ".json"
  }
}