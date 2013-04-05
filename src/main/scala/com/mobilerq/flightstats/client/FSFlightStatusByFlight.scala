package com.mobilerq.flightstats.client

import org.joda.time.DateTime
import com.ning.http.client.{Request, RequestBuilder}
import com.mobilerq.flightstats.client._
import com.mobilerq.flightstats.api.v1._
import com.mobilerq.flightstats.api.v1.flightstatus._
import com.google.common.cache.CacheBuilder

/** Factory for [[com.mobilerq.flightstats.client.FSFlightStatusByFlight]] instances. */
object FSFlightStatusByFlight {
  def apply(appId: String, appKey: String): FSFlightStatusByFlight = {
    new FSFlightStatusByFlight(appId, appKey) with FSClientReboot
  }

  def apply(appId: String, appKey: String, cacheBuilder: CacheBuilder[Object, Object]) = {
    new FSFlightStatusByFlight(appId, appKey)
      with FSClientReboot
      with FSCaching {
        override protected val cache = cacheBuilder.build(loader)
    }
  }
}

/** A client for FlightStats Flight Status & Track by Flight API
  *
  * Use companion object to obtain an instance:
  *
  * {{{
  *   val statusClient = FSFlightStatusByFlight("my app id", "my app key")
  * }}}
  *
  * @see <a target="_top" href="https://developer.flightstats.com/api-docs/flightstatus/v2/flight">FlightStats Flight Status & Track by Flight API Documentation</a>
  */
abstract class FSFlightStatusByFlight(protected val appId: String, protected val appKey: String)
  extends FSClientBase with FSFlightStatusHelpers with FSFlightTrackHelpers {
  // https://api.flightstats.com/flex/flightstatus/rest/v2/json
  protected def api = fsHost / "flex" / "flightstatus" / "rest" / "v2" / "json"


  /** Flight Status (by flight ID)
    *
    * /v2/json/flight/status/{flightId} GET
    */
  def flightStatus(code: Long, args: ArgMap = Map.empty) =
    status( api / "flight" / "status" / code.toString <<? args)

  /** Flight status (flights departing on date)
    *
    * /v2/json/flight/status/{carrier}/{flight}/dep/{year}/{month}/{day} GET
    */
  def flightStatusDepartingOnDate(carrier: String, flight: String, date: DateTime, args: ArgMap = Map.empty) =
    statuses( api / "flight" / "status" / carrier / flight / "dep" / date <<? args )

  /** Flight status (flights arriving on date)
    *
    * /v2/json/flight/status/{carrier}/{flight}/arr/{year}/{month}/{day} GET
    */
  def flightStatusArrivingOnDate(carrier: String, flight: String, date: DateTime, args: ArgMap = Map.empty) =
    statuses( api / "flight" / "status" / carrier / flight / "arr" / date <<? args)

  /** Flight track (by flight ID)
    *
    * /v2/json/flight/track/{flightId} GET
    */
  def flightTrack(code: Long, args: ArgMap = Map.empty) =
    track( api / "flight" / "track" / code.toString <<? args)

  /** Flight tracks arriving on date (starting from specified hour of day)
    *
    * /v2/json/flight/tracks/{carrier}/{flight}/arr/{year}/{month}/{day} GET
    */
  def flightTracksArrivingOnDate(carrier: String, flight: String, date: DateTime, args: ArgMap = Map.empty) =
    tracks( api / "flight" / "tracks" / carrier / flight / "arr" / date <<? args )

  /** Flight tracks departing on date (starting from specified hour of day)
    *
    * /v2/json/flight/tracks/{carrier}/{flight}/dep/{year}/{month}/{day} GET
    */
  def flightTracksDepartingOnDate(carrier: String, flight: String, date: DateTime, args: ArgMap = Map.empty) =
    tracks( api / "flight" / "tracks" / carrier / flight / "dep" / date <<? args )
}
