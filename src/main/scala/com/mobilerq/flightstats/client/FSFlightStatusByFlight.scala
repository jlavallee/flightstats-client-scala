package com.mobilerq.flightstats.client

import dispatch._
import org.joda.time.DateTime
import com.ning.http.client.RequestBuilder
import com.mobilerq.flightstats.client._
import com.mobilerq.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}

/** Factory for [[com.mobilerq.flightstats.client.FSFlightStatusByFlight]] instances. */
object FSFlightStatusByFlight {
  def apply(appId: String, appKey: String): FSFlightStatusByFlight = {
    new FSFlightStatusByFlight(appId, appKey) with FSClientReboot
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
abstract class FSFlightStatusByFlight(protected val appId: String, protected val appKey: String) extends FSClientBase {
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


  private def status(url: RequestBuilder) =
    getAndDeserialize(classOf[FSFlightStatusResponse], url)

  private def statuses(url: RequestBuilder) =
    getAndDeserialize(classOf[FSFlightStatusesResponse], url)

  private def track(url: RequestBuilder) =
    getAndDeserialize(classOf[FSFlightTrackResponse], url)

  private def tracks(url: RequestBuilder) =
    getAndDeserialize(classOf[FSFlightTracksResponse], url)
}
