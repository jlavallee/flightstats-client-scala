package com.flightstats.client

import dispatch._
import org.joda.time.DateTime
import com.ning.http.client.RequestBuilder
import com.flightstats.client._
import com.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}

/** Factory for [[com.flightstats.client.FSFlightStatusByAirport]] instances. */
object FSFlightStatusByAirport {
  def apply(appId: String, appKey: String): FSFlightStatusByAirport = {
    new FSFlightStatusByAirport(appId, appKey) with FSClientReboot
  }
}
/**
 * A client for FlightStats Flight Status & Track by Airport API
 *
 * Use companion object to obtain an instance:
 *
 * {{{
 *   val statusClient = FSFlightStatuses("my app id", "my app key")
 * }}}
 *
 * @see <a target="_top" href="https://developer.flightstats.com/api-docs/flightstatus/v2/airport">FlightStats Flight Status & Track by Airport API Documentation</a>
 */
abstract class FSFlightStatusByAirport(protected val appId: String, protected val appKey: String) extends FSClientBase {
  // https://api.flightstats.com/flex/flightstatus/rest/v2/json
  def api = fsHost / "flex" / "flightstatus" / "rest" / "v2" / "json"

  /** Airport status (departures)
    *
    * /v2/json/airport/status/{airport}/dep/{year}/{month}/{day}/{hourOfDay} GET
    */
  def departureStatus(airportCode: String, date: DateTime) =
    statuses(api / "airport" / "status" / airportCode / "dep" / date / date.toString("HH"))

  /** Airport status (arrivals)
    *
    * /v2/json/airport/status/{airport}/arr/{year}/{month}/{day}/{hourOfDay} GET
    */
  def arrivalStatus(airportCode: String, date: DateTime) =
    statuses(api / "airport" / "status" / airportCode / "arr" / date / date.toString("HH"))

  /** Airport tracks (arrivals)
    *
    * /v2/json/airport/tracks/{airport}/arr GET
    */
  def arrivalTracks(airportCode: String) =
    tracks(api / "airport" / "tracks" / airportCode / "arr" )

  /** Airport tracks (departures)
    *
    * /v2/json/airport/tracks/{airport}/dep GET
    */
  def departureTracks(airportCode: String) =
    tracks(api / "airport" / "tracks" / airportCode / "dep" )


  private def statuses(url: RequestBuilder) =
    getAndDeserialize(classOf[FSFlightStatusesResponse], url)
    
  private def tracks(url: RequestBuilder) =
    getAndDeserialize(classOf[FSFlightTracksResponse], url)
}
