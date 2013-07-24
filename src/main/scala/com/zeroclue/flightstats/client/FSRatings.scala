package com.zeroclue.flightstats.client

import dispatch._
import org.joda.time.DateTime
import com.zeroclue.flightstats.client._
import com.zeroclue.flightstats.api.v1.ratings.{FSRatingsForFlight, FSRatingsForRoute}
import com.google.common.cache.CacheBuilder

/** Factory for [[com.zeroclue.flightstats.client.FSRatings]] instances. */
object FSRatings {
  def apply(appId: String, appKey: String): FSRatings = {
    new FSRatings(appId, appKey)
      with HttpClientReboot
      with JacksonMapper
  }

  def apply(appId: String, appKey: String, cacheBuilder: CacheBuilder[Object, Object]) = {
    new FSRatings(appId, appKey)
      with HttpClientReboot
      with JacksonMapper
      with FSCaching {
        override val cache = cacheBuilder.build(loader)
    }
  }
}

/** A client for FlightStats Ratings API
  *
  * Use companion object to obtain an instance:
  *
  * {{{
  *   val ratingsClient = FSRatings("my app id", "my app key")
  * }}}
  *
  * @see <a target="_top" href="https://developer.flightstats.com/api-docs/ratings/v1">FlightStats Ratings API Documentation</a>
  */
abstract class FSRatings(protected val appId: String, protected val appKey: String) extends FSClientBase {
  // https://api.flightstats.com/flex/ratings/rest/v1/json
  protected def api = fsHost / "flex" / "ratings" / "rest" / "v1" / "json"


  /** Ratings for flight
    *
    * /v1/json/flight/{carrier}/{flightNumber} GET
    */
  def forFlight(carrier: String, flightNumber: String, args: ArgMap = Map.empty) =
    getAndDeserialize(classOf[FSRatingsForFlight],
        api / "flight" / carrier / flightNumber <<? args
    )

  /** Ratings for route
    *
    * /v1/json/route/{departureAirport}/{arrivalAirport} GET
    */
  def forRoute(departureAirport: String, arrivalAirport: String, args: ArgMap = Map.empty) =
    getAndDeserialize(classOf[FSRatingsForRoute],
        api / "route" / departureAirport / arrivalAirport <<? args
    )


}
