package com.zeroclue.flightstats.client

import org.joda.time.DateTime
import com.zeroclue.flightstats.client._
import com.zeroclue.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}
import com.google.common.cache.CacheBuilder

/** Factory for [[com.zeroclue.flightstats.client.FSFlightStatusByAirport]] instances. */
object FSFlightStatusByAirport {
  def apply(appId: String, appKey: String): FSFlightStatusByAirport = {
    new FSFlightStatusByAirport(appId, appKey)
      with HttpClientReboot
      with JacksonMapper
  }

  def apply(appId: String, appKey: String, cacheBuilder: CacheBuilder[Object, Object]) = {
    new FSFlightStatusByAirport(appId, appKey)
      with HttpClientReboot
      with JacksonMapper
      with FSCaching {
        override val cache = cacheBuilder.build(loader)
    }
  }
}

/** A client for FlightStats Flight Status & Track by Airport API
  *
  * Use companion object to obtain an instance:
  *
  * {{{
  *   val statusClient = FSFlightStatusByAirport("my app id", "my app key")
  * }}}
  *
  * @see <a target="_top" href="https://developer.flightstats.com/api-docs/flightstatus/v2/airport">FlightStats Flight Status & Track by Airport API Documentation</a>
  */
abstract class FSFlightStatusByAirport(protected val appId: String, protected val appKey: String)
  extends FSClientBase with FSFlightStatusHelpers with FSFlightTrackHelpers {
  // https://api.flightstats.com/flex/flightstatus/rest/v2/json
  protected def api = fsHost / "flex" / "flightstatus" / "rest" / "v2" / "json"

  /** Airport status (departures)
    *
    * /v2/json/airport/status/{airport}/dep/{year}/{month}/{day}/{hourOfDay} GET
    */
  def departureStatus(airportCode: String, date: DateTime, args: ArgMap = Map.empty) =
    statuses( api / "airport" / "status" / airportCode / "dep" / date / date.toString("HH") <<? args )

  /** Airport status (arrivals)
    *
    * /v2/json/airport/status/{airport}/arr/{year}/{month}/{day}/{hourOfDay} GET
    */
  def arrivalStatus(airportCode: String, date: DateTime, args: ArgMap = Map.empty) =
    statuses( api / "airport" / "status" / airportCode / "arr" / date / date.toString("HH") <<? args )

  /** Airport tracks (arrivals)
    *
    * /v2/json/airport/tracks/{airport}/arr GET
    */
  def arrivalTracks(airportCode: String, args: ArgMap = Map.empty) =
    tracks( api / "airport" / "tracks" / airportCode / "arr" <<? args )

  /** Airport tracks (departures)
    *
    * /v2/json/airport/tracks/{airport}/dep GET
    */
  def departureTracks(airportCode: String, args: ArgMap = Map.empty) =
    tracks( api / "airport" / "tracks" / airportCode / "dep" <<? args )
}