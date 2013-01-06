package com.flightstats.client

import org.scalatest.Assertions
import dispatch.Promise

trait FSTest extends Assertions {
  val appId = sys.props.get("flightstats.appid")
  val appKey = sys.props.get("flightstats.appkey")

  def debug(promise: Promise[Either[Throwable, AnyRef]]) {
    if(appId.isDefined && appKey.isDefined)
      println(promise) // it's cool to see that we're really async when we test w/creds
  }
}

trait FSTestRun extends FSClient {
  override def extendedOptions = Seq("testRun")
}