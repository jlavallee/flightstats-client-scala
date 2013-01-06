package com.flightstats.client

import dispatch._
import com.ning.http.client.RequestBuilder
import com.flightstats.api.v1.FSAirport
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.flightstats.api.v1.FSAirportsHolder

object FSAirports {
  def apply(appId: String, appKey: String): FSAirports = {
    new FSAirports(appId, appKey) with FSClientReboot with JacksonMapper
  }
}

abstract class FSAirports(val appId: String, val appKey: String) extends FSClient {
  // https://api.flightstats.com/flex/airports/rest/v1/json
  def apiLocation = Seq("flex", "airports", "rest", "v1", "json")
  // /active?appId=<xxxxx>&appKey=<yyyyy>
  def active: Promise[Either[Throwable, Seq[FSAirport]]] =
    for {
      a <- getToJson(classOf[FSAirportsHolder], "active").right
    } yield a.airports

  // /v1/json/active/{year}/{month}/{day} GET
}
