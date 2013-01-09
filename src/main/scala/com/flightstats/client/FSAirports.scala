package com.flightstats.client

import dispatch._
import org.joda.time.DateTime
import com.ning.http.client.RequestBuilder
import com.flightstats.client._
import com.flightstats.api.v1.{FSAirport, FSAirportHolder, FSAirportsHolder}

object FSAirports {
  def apply(appId: String, appKey: String): FSAirports = {
    new FSAirports(appId, appKey) with FSClientReboot with JacksonMapper
  }
}

abstract class FSAirports(val appId: String, val appKey: String) extends FSClientBase {
  // https://api.flightstats.com/flex/airports/rest/v1/json
  def apiLocation = Seq("flex", "airports", "rest", "v1", "json")


  /** Active airports
   * /v1/json/active GET
   */
  def active: Promise[Seq[FSAirport]] =
    airportsFor(fs / "active")

  /** Active airports for date
   * /v1/json/active/{year}/{month}/{day} GET
   */
  def activeOnDate(date: DateTime): Promise[Seq[FSAirport]] =
    airportsFor(fs / "active" / date)

  /** All airports (active and inactive)
   * /v1/json/all GET
   */
  def all: Promise[Seq[FSAirport]] =
    airportsFor(fs / "all")

  /** Current airport by code (code type chosen via precedence order)
   * /v1/json/{code}/today GET
   */
  def byCode(code: String): Promise[FSAirport] =
    airportFor(fs / code / "today")

  /** Airport on date by code (code type chosen via precedence order)
   * /v1/json/{code}/{year}/{month}/{day} GET
   */
  def onDateByCode(date: DateTime, code: String): Promise[FSAirport] =
    airportFor(fs / code / date )

  /** Airports by city code
   * /v1/json/cityCode/{cityCode} GET
   */
  def byCityCode(code: String): Promise[Seq[FSAirport]] =
    airportsFor(fs / "cityCode" / code)

  /** Airports by country code
   * /v1/json/countryCode/{countryCode} GET
   */
  def byCountryCode(code: String): Promise[Seq[FSAirport]] =
    airportsFor(fs / "countryCode" / code)

  /** Airport by FlightStats code
   * /v1/json/fs/{code} GET
   */
  def byFlightStatsCode(code: String): Promise[FSAirport] =
    airportFor(fs / "fs" / code)

  /** Airports by IATA code
   * /v1/json/iata/{iataCode} GET
   */
  def byIataCode(code: String): Promise[Seq[FSAirport]] =
    airportsFor(fs / "iata" / code)

  /** Airport by IATA code on date
   * /v1/json/iata/{iataCode}/{year}/{month}/{day} GET
   */
  def byIataCodeOnDate(code: String, date: DateTime): Promise[FSAirport] =
    airportFor(fs / "iata" / code / date)


  /** Airports by ICAO code
   * /v1/json/icao/{icaoCode} GET
   */
  def byIcaoCode(code: String): Promise[Seq[FSAirport]] =
    airportsFor(fs / "icao" / code)

  /** Airport by ICAO code on date
   * /v1/json/icao/{icaoCode}/{year}/{month}/{day} GET
   */
  def byIcaoCodeOnDate(code: String, date: DateTime): Promise[FSAirport] =
    airportFor(fs / "icao" / code / date)

  /** Airports within radius of location
   * /v1/json/withinRadius/{longitude}/{latitude}/{radiusMiles} GET
   */
  def withinRadius(long: Integer, lat: Integer, radius: Integer): Promise[Seq[FSAirport]] =
    airportsFor(fs / "withinRadius" / long.toString() / lat.toString() / radius.toString())

  private def airportsFor(url: RequestBuilder) =
    for ( a <- getAndDeserialize(classOf[FSAirportsHolder], url) ) yield a.airports

  private def airportFor(url: RequestBuilder) =
    for ( a <- getAndDeserialize(classOf[FSAirportHolder], url) ) yield a.airport
}
