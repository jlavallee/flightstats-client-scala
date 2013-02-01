package com.mobilerq.flightstats.client

import dispatch._
import org.joda.time.DateTime
import com.ning.http.client.RequestBuilder
import com.mobilerq.flightstats.client._
import com.mobilerq.flightstats.api.v1.{FSAirport, FSAirportHolder, FSAirportsHolder}

/** Factory for [[com.mobilerq.flightstats.client.FSAirports]] instances. */
object FSAirports {
  def apply(appId: String, appKey: String): FSAirports = {
    new FSAirports(appId, appKey) with FSClientReboot
  }
}
/** A client for FlightStats Airports API
  *
  * Use companion object to obtain an instance:
  *
  * {{{
  *   val airportsClient = FSAirports("my app id", "my app key")
  * }}}
  *
  * @see <a target="_top" href="https://developer.flightstats.com/api-docs/airports/v1">FlightStats Airports API Documentation</a>
  */
abstract class FSAirports(protected val appId: String, protected val appKey: String) extends FSClientBase {
  // https://api.flightstats.com/flex/airports/rest/v1/json
  protected def api = fsHost / "flex" / "airports" / "rest" / "v1" / "json"

  /** Active airports
    *
    * /v1/json/active GET
    */
  def active: Promise[Seq[FSAirport]] =
    airportsFor(api / "active")

  /** Active airports for date
    *
    * /v1/json/active/{year}/{month}/{day} GET
    */
  def activeOnDate(date: DateTime): Promise[Seq[FSAirport]] =
    airportsFor(api / "active" / date)

  /** All airports (active and inactive)
    *
    * /v1/json/all GET
    */
  def all: Promise[Seq[FSAirport]] =
    airportsFor(api / "all")

  /** Current airport by code (code type chosen via precedence order)
    *
    * /v1/json/{code}/today GET
    */
  def byCode(code: String): Promise[FSAirport] =
    airportFor(api / code / "today")

  /** Airport on date by code (code type chosen via precedence order)
    *
    * /v1/json/{code}/{year}/{month}/{day} GET
    */
  def onDateByCode(date: DateTime, code: String): Promise[FSAirport] =
    airportFor(api / code / date )

  /** Airports by city code
    *
    * /v1/json/cityCode/{cityCode} GET
    */
  def byCityCode(code: String): Promise[Seq[FSAirport]] =
    airportsFor(api / "cityCode" / code)

  /** Airports by country code
    *
    * /v1/json/countryCode/{countryCode} GET
    */
  def byCountryCode(code: String): Promise[Seq[FSAirport]] =
    airportsFor(api / "countryCode" / code)

  /** Airport by FlightStats code
    *
    * /v1/json/fs/{code} GET
    */
  def byFlightStatsCode(code: String): Promise[FSAirport] =
    airportFor(api / "fs" / code)

  /** Airports by IATA code
    *
    * /v1/json/iata/{iataCode} GET
    */
  def byIataCode(code: String): Promise[Seq[FSAirport]] =
    airportsFor(api / "iata" / code)

  /** Airport by IATA code on date
    *
    * /v1/json/iata/{iataCode}/{year}/{month}/{day} GET
    */
  def byIataCodeOnDate(code: String, date: DateTime): Promise[FSAirport] =
    airportFor(api / "iata" / code / date)


  /** Airports by ICAO code
    *
    * /v1/json/icao/{icaoCode} GET
    */
  def byIcaoCode(code: String): Promise[Seq[FSAirport]] =
    airportsFor(api / "icao" / code)

  /** Airport by ICAO code on date
    *
    * /v1/json/icao/{icaoCode}/{year}/{month}/{day} GET
    */
  def byIcaoCodeOnDate(code: String, date: DateTime): Promise[FSAirport] =
    airportFor(api / "icao" / code / date)

  /** Airports within radius of location
    *
    * /v1/json/withinRadius/{longitude}/{latitude}/{radiusMiles} GET
    */
  def withinRadius(long: Integer, lat: Integer, radius: Integer): Promise[Seq[FSAirport]] =
    airportsFor(api / "withinRadius" / long.toString() / lat.toString() / radius.toString())

  private def airportsFor(url: RequestBuilder) =
    for ( a <- getAndDeserialize(classOf[FSAirportsHolder], url) ) yield a.airports

  private def airportFor(url: RequestBuilder) =
    for ( a <- getAndDeserialize(classOf[FSAirportHolder], url) ) yield a.airport
}
