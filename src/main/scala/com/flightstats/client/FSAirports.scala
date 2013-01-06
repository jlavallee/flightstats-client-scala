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


  /** Active airports
   * /v1/json/active GET
   */
  def active: Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor("active")

  /** Active airports for date
   * /v1/json/active/{year}/{month}/{day} GET
   */
  def activeOnDate(date: DateTime): Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor(("active" :: datePieces(date)):_*)

  /** All airports (active and inactive)
   * /v1/json/all GET
   */
  def all: Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor("all")

  /** Current airport by code (code type chosen via precedence order)
   * /v1/json/{code}/today GET
   */
  def byCode(code: String): Promise[Either[Throwable, FSAirport]] =
    airportFor(code, "today")

  /** Airport on date by code (code type chosen via precedence order)
   * /v1/json/{code}/{year}/{month}/{day} GET
   */
  def onDateByCode(date: DateTime, code: String): Promise[Either[Throwable, FSAirport]] =
    airportFor((code :: datePieces(date)):_*)

  /** Airports by city code
   * /v1/json/cityCode/{cityCode} GET
   */
  def byCityCode(code: String): Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor("cityCode", code)

  /** Airports by country code
   * /v1/json/countryCode/{countryCode} GET
   */
  def byCountryCode(code: String): Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor("countryCode", code)

  /** Airport by FlightStats code
   * /v1/json/fs/{code} GET
   */
  def byFlightStatsCode(code: String): Promise[Either[Throwable, FSAirport]] =
    airportFor("fs", code)

  /** Airports by IATA code
   * /v1/json/iata/{iataCode} GET
   */
  def byIataCode(code: String): Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor("iata", code)

  /** Airport by IATA code on date
   * /v1/json/iata/{iataCode}/{year}/{month}/{day} GET
   */
  def byIataCodeOnDate(code: String, date: DateTime): Promise[Either[Throwable, FSAirport]] =
    airportFor(("iata" :: (code :: datePieces(date))):_*)

  /** Airports by ICAO code
   * /v1/json/icao/{icaoCode} GET
   */
  def byIcaoCode(code: String): Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor("icao", code)

  /** Airport by ICAO code on date
   * /v1/json/icao/{icaoCode}/{year}/{month}/{day} GET
   */
  def byIcaoCodeOnDate(code: String, date: DateTime): Promise[Either[Throwable, FSAirport]] =
    airportFor(("icao" :: (code :: datePieces(date))):_*)

  /** Airports within radius of location
   * /v1/json/withinRadius/{longitude}/{latitude}/{radiusMiles} GET
   */
  def withinRadius(long: Integer, lat: Integer, radius: Integer): Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor("withinRadius", long.toString(), lat.toString(), radius.toString())

  private def airportsFor(pathPieces: String*) =
    for ( a <- getToJson(classOf[FSAirportsHolder], pathPieces:_*).right ) yield a.airports

  private def airportFor(pathPieces: String*) =
    for ( a <- getToJson(classOf[FSAirportHolder], pathPieces:_*).right ) yield a.airport
}
