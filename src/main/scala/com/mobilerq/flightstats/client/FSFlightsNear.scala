package com.mobilerq.flightstats.client

import dispatch._
import org.joda.time.DateTime
import com.ning.http.client.RequestBuilder
import com.mobilerq.flightstats.client._
import com.mobilerq.flightstats.api.v1.flightstatus.FSFlightsNearBoundingBox
import com.mobilerq.flightstats.api.v1.flightstatus.FSFlightsNearPointAndDistance
import com.google.common.cache.CacheBuilder

/** Factory for [[com.mobilerq.flightstats.client.FSFlightsNear]] instances. */
object FSFlightsNear {
  def apply(appId: String, appKey: String): FSFlightsNear = {
    new FSFlightsNear(appId, appKey) with FSClientReboot
  }

  def apply(appId: String, appKey: String, cacheBuilder: CacheBuilder[Object, Object]) = {
    new FSFlightsNear(appId, appKey)
      with FSClientReboot
      with FSCaching {
        override val cache = cacheBuilder.build(loader)
    }
  }
}

/** A client for FlightStats Flights Near (Flight Positions) API
  *
  * Use companion object to obtain an instance:
  *
  * {{{
  *   val statusClient = FSFlightsNear("my app id", "my app key")
  * }}}
  *
  * @see <a target="_top" href="https://developer.flightstats.com/api-docs/flightstatus/v2/flightsNear">FlightStats Flights Near (Flight Positions) API Documentation</a>
  */
abstract class FSFlightsNear(protected val appId: String, protected val appKey: String) extends FSClientBase {
  // https://api.flightstats.com/flex/flightstatus/rest/v2/json
  protected def api = fsHost / "flex" / "flightstatus" / "rest" / "v2" / "json"

  def boundingBox(topLat: BigDecimal, leftLon: BigDecimal, bottomLat: BigDecimal, rightLon: BigDecimal, args: ArgMap = Map.empty) =
    getAndDeserialize(classOf[FSFlightsNearBoundingBox],
            api / "flightsNear" / topLat / leftLon / bottomLat / rightLon <<? args )

  def pointAndDistance(lat: BigDecimal, lon: BigDecimal, miles: Integer, args: ArgMap = Map.empty) =
    getAndDeserialize(classOf[FSFlightsNearPointAndDistance],
            api / "flightsNear" / lat / lon / miles.toString() <<? args )
}
