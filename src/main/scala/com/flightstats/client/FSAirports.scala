package com.flightstats.client

import dispatch._
import com.flightstats.client._
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
    airportsFor(url / "active")

  /** Active airports for date
   * /v1/json/active/{year}/{month}/{day} GET
   */
  def activeOnDate(date: DateTime): Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor(url / "active" / date)

  /** All airports (active and inactive)
   * /v1/json/all GET
   */
  def all: Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor(url / "all")

  /** Current airport by code (code type chosen via precedence order)
   * /v1/json/{code}/today GET
   */
  def byCode(code: String): Promise[Either[Throwable, FSAirport]] =
    airportFor(url / code / "today")

  /** Airport on date by code (code type chosen via precedence order)
   * /v1/json/{code}/{year}/{month}/{day} GET
   */
  def onDateByCode(date: DateTime, code: String): Promise[Either[Throwable, FSAirport]] =
    airportFor(url / code / date )

  /** Airports by city code
   * /v1/json/cityCode/{cityCode} GET
   */
  def byCityCode(code: String): Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor(url / "cityCode" / code)

  /** Airports by country code
   * /v1/json/countryCode/{countryCode} GET
   */
  def byCountryCode(code: String): Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor(url / "countryCode" / code)

  /** Airport by FlightStats code
   * /v1/json/fs/{code} GET
   */
  def byFlightStatsCode(code: String): Promise[Either[Throwable, FSAirport]] =
    airportFor(url / "fs" / code)

  /** Airports by IATA code
   * /v1/json/iata/{iataCode} GET
   */
  def byIataCode(code: String): Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor(url / "iata" / code)

  /** Airport by IATA code on date
   * /v1/json/iata/{iataCode}/{year}/{month}/{day} GET
   */
  def byIataCodeOnDate(code: String, date: DateTime): Promise[Either[Throwable, FSAirport]] =
    airportFor(url / "iata" / code / date)


  /** Airports by ICAO code
   * /v1/json/icao/{icaoCode} GET
   */
  def byIcaoCode(code: String): Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor(url / "icao" / code)

  /** Airport by ICAO code on date
   * /v1/json/icao/{icaoCode}/{year}/{month}/{day} GET
   */
  def byIcaoCodeOnDate(code: String, date: DateTime): Promise[Either[Throwable, FSAirport]] =
    airportFor(url / "icao" / code / date)

  /** Airports within radius of location
   * /v1/json/withinRadius/{longitude}/{latitude}/{radiusMiles} GET
   */
  def withinRadius(long: Integer, lat: Integer, radius: Integer): Promise[Either[Throwable, Seq[FSAirport]]] =
    airportsFor(url / "withinRadius" / long.toString() / lat.toString() / radius.toString())

  private def airportsFor(url: RequestBuilder) =
    for ( a <- getAndDeserialize(classOf[FSAirportsHolder], url).right ) yield a.airports

  private def airportFor(url: RequestBuilder) =
    for ( a <- getAndDeserialize(classOf[FSAirportHolder], url).right ) yield a.airport
}
