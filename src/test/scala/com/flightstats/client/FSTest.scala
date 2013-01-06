package com.flightstats.client

import org.scalatest.Assertions

trait FSTest extends Assertions {
  val appId = sys.props.get("flightstats.appid")
  val appKey = sys.props.get("flightstats.appkey")
}