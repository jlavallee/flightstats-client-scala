package com.flightstats.client

import org.scalatest.Assertions
import dispatch.Promise
import com.ning.http.client.RequestBuilder

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

  override def getWithCreds(url: RequestBuilder) : Promise[Either[Throwable, String]] =
    for (x <- super.getWithCreds(url).right) yield {
      capture match {
        case Some(v) if v.toBoolean => capture(url, x)
        case _ => x
      }
    }

  // write the string to a file & return it
  def capture(url: RequestBuilder, contents: String): String = {
    import sys.process._
    contents #> new java.io.File(filePathForUrl(url)) !

    contents
  }
}

trait FSStaticTestJson {
  val startDir = "src/test/resources/"
  def filePathForUrl(url: RequestBuilder): String = {
    val queryPathWithHost =  url.build().getRawUrl().split("://")(1)
    return startDir + queryPathWithHost.substring(queryPathWithHost.indexOf("/"))
  }
}