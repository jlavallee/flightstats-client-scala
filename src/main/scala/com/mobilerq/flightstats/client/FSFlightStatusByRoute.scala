package com.mobilerq.flightstats.client

import dispatch._
import org.joda.time.DateTime
import com.ning.http.client.RequestBuilder
import com.mobilerq.flightstats.client._
import com.mobilerq.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}

/** Factory for [[com.mobilerq.flightstats.client.FSFlightStatusByRoute]] instances. */
object FSFlightStatusByRoute {
  def apply(appId: String, appKey: String): FSFlightStatusByRoute = {
    new FSFlightStatusByRoute(appId, appKey) with FSClientReboot
  }
}

/** A client for FlightStats Flight Status by Route API
  *
  * Use companion object to obtain an instance:
  *
  * {{{
  *   val statusClient = FSFlightStatusByRoute("my app id", "my app key")
  * }}}
  *
  * @see <a target="_top" href="https://developer.flightstats.com/api-docs/flightstatus/v2/route">FlightStats Flight Status by Route API Documentation</a>
  */
abstract class FSFlightStatusByRoute(protected val appId: String, protected val appKey: String) extends FSClientBase {
  // https://api.flightstats.com/flex/flightstatus/rest/v2/json
  protected def api = fsHost / "flex" / "flightstatus" / "rest" / "v2" / "json"

  /** Route status by departure date
    *
    * /v2/json/route/status/{departureAirport}/{arrivalAirport}/dep/{year}/{month}/{day} GET
    */
  def departureDate(departureAirportCode: String, arrivalAirportCode: String, date: DateTime, args: ArgMap = Map.empty) =
    statuses( api / "route" / "status" / departureAirportCode / arrivalAirportCode / "dep" / date <<? args )

  /** Route status by arrival date
    *
    * /v2/json/route/status/{departureAirport}/{arrivalAirport}/arr/{year}/{month}/{day} GET
    */
  def arrivalDate(departureAirportCode: String, arrivalAirportCode: String, date: DateTime, args: ArgMap = Map.empty) =
    statuses( api / "route" / "status" / departureAirportCode / arrivalAirportCode / "arr" / date <<? args )


  private def statuses(url: RequestBuilder) =
    getAndDeserialize(classOf[FSFlightStatusesResponse], url)

}
