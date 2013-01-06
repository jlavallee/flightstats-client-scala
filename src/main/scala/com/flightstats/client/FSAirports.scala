package com.flightstats.client

import dispatch._
import com.ning.http.client.RequestBuilder
import com.flightstats.api.v1.FSAirport
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.flightstats.api.v1.FSAirportsHolder
import org.joda.time.DateTime
import com.flightstats.api.v1.FSAirportHolder

object FSAirports {
  def apply(appId: String, appKey: String): FSAirports = {
    new FSAirports(appId, appKey) with FSClientReboot with JacksonMapper
  }
}

abstract class FSAirports(val appId: String, val appKey: String) extends FSClient {
  // https://api.flightstats.com/flex/airports/rest/v1/json
  def apiLocation = Seq("flex", "airports", "rest", "v1", "json")

  // /active
  def active: Promise[Either[Throwable, Seq[FSAirport]]] =
    for { a <- getToJson(classOf[FSAirportsHolder], "active").right } yield a.airports

  // /v1/json/active/{year}/{month}/{day} GET
  def active(date: DateTime): Promise[Either[Throwable, Seq[FSAirport]]] =
    for { a <- getToJson(classOf[FSAirportsHolder], ("active" :: datePieces(date)):_*).right } yield a.airports

  // /v1/json/all
  def all: Promise[Either[Throwable, Seq[FSAirport]]] =
    for { a <- getToJson(classOf[FSAirportsHolder], "all").right } yield a.airports

  // /v1/json/{code}/today 
  def byCode(code: String): Promise[Either[Throwable, FSAirport]] =
    for { a <- getToJson(classOf[FSAirportHolder], code, "today").right} yield a.airport

  // /v1/json/{code}/{year}/{month}/{day}
  def onDateByCode(date: DateTime, code: String): Promise[Either[Throwable, FSAirport]] =
    for { a <- getToJson(classOf[FSAirportHolder], (code :: datePieces(date)):_*).right} yield a.airport
}
