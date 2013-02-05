package com.mobilerq.flightstats.client

import dispatch._
import org.joda.time.DateTime
import com.ning.http.client.RequestBuilder
import com.mobilerq.flightstats.client._
import com.mobilerq.flightstats.api.v1.connections.FSConnectionsResponse

/** Factory for [[com.mobilerq.flightstats.client.FSConnections]] instances. */
object FSConnections {
  def apply(appId: String, appKey: String): FSConnections = {
    new FSConnections(appId, appKey) with FSClientReboot
  }
}

/** A client for FlightStats Schedules / Connections API
  *
  * Use companion object to obtain an instance:
  *
  * {{{
  *   val connectionsClient = FSConnections("my app id", "my app key")
  * }}}
  *
  * @see <a target="_top" href="https://developer.flightstats.com/api-docs/connections/v1">FlightStats Connections API Documentation</a>
  */
abstract class FSConnections(protected val appId: String, protected val appKey: String) extends FSClientBase {
  // https://api.flightstats.com/flex/connections/rest/v1/json
  protected def api = fsHost / "flex" / "connections" / "rest" / "v1" / "json"

  /** Direct scheduled flights by arrival location and date
    *
    * /v1/json/direct/to/{arrivalCode}/arriving/{year}/{month}/{day} GET
    */
  def directByArrivalAndDate(arrivalCode: String, date: DateTime, args: ArgMap = Map.empty) =
    flights(api / "direct" / "to" / arrivalCode / "arriving" / date <<? args)

  /** Direct scheduled flights by departure location and departure date
    *
    * /v1/json/direct/from/{departureCode}/departing/{year}/{month}/{day} GET
    */
  def directByDepartureAndDate(departureCode: String, date: DateTime, args: ArgMap = Map.empty) =
    flights(api / "direct" / "from" / departureCode / "departing" / date <<? args)

  /** Direct scheduled flights by carrier, flight number, and arrival date(s)
    *
    * /v1/json/direct/flight/{carrier}/{flightNumber}/arriving/{year}/{month}/{day} GET
    */
  def directByCarrierAndFlightNumberAndArrivalDate(carrier: String, flightNumber: String, date: DateTime, args: ArgMap = Map.empty) =
    flights(api / "direct" / "flight" / carrier / flightNumber / "arriving" / date <<? args)

  /** Direct scheduled flights by carrier, flight number, arrival location and arrival date(s)
    *
    * /v1/json/direct/flight/{carrier}/{flightNumber}/to/{arrivalCode}/arriving/{year}/{month}/{day} GET
    */
  def directByCarrierAndFlightNumberAndArrivalLocationAndDate(carrier: String, flightNumber: String, arrivalCode: String, date: DateTime, args: ArgMap = Map.empty) =
    flights(api / "direct" / "flight" / carrier / flightNumber / "to" / arrivalCode / "arriving" / date <<? args)

  /** Direct scheduled flights by carrier, flight number and departure date(s)
    *
    * /v1/json/direct/flight/{carrier}/{flightNumber}/departing/{year}/{month}/{day} GET
    */
  def directByCarrierAndFlightNumberAndDepartureDate(carrier: String, flightNumber: String, date: DateTime, args: ArgMap = Map.empty) =
    flights(api / "direct" / "flight" / carrier / flightNumber / "departing" / date <<? args)

  /** Direct scheduled flights by carrier, flight number, departure location and departure date(s)
    *
    * /v1/json/direct/flight/{carrier}/{flightNumber}/from/{departureCode}/departing/{year}/{month}/{day} GET
    */
  def directByCarrierAndFlightNumberAndDepartureLocationAndDate(carrier: String, flightNumber: String, departureCode: String, date: DateTime, args: ArgMap = Map.empty) =
    flights(api / "direct" / "flight" / carrier / flightNumber / "from" / departureCode / "departing" / date <<? args)

  /** Direct and connecting scheduled flights between two locations by arrival date
    *
    * /v1/json/connecting/from/{departureCode}/to/{arrivalCode}/arriving/{year}/{month}/{day} GET
    */
  def betweenLocationsByArrivalDate(departureCode: String, arrivalCode: String, date: DateTime, args: ArgMap = Map.empty) =
    flights(api / "connecting" / "from" / departureCode / "to" / arrivalCode / "arriving" / date)

  /** Direct and connecting scheduled flights between two locations by departure date
    *
    * /v1/json/connecting/from/{departureCode}/to/{arrivalCode}/departing/{year}/{month}/{day} GET
    */
  def betweenLocationsByDepartureDate(departureCode: String, arrivalCode: String, date: DateTime, args: ArgMap = Map.empty) =
    flights(api / "connecting" / "from" / departureCode / "to" / arrivalCode / "departing" / date)

  private def flights(url: RequestBuilder) =
    getAndDeserialize(classOf[FSConnectionsResponse], url)

}
