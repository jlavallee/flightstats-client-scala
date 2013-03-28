package com.mobilerq.flightstats.client

import scala.concurrent.Future
import dispatch._
import com.ning.http.client.RequestBuilder
import com.mobilerq.flightstats.api.v1.delayindex.FSDelayIndexResponse

/** Factory for [[com.mobilerq.flightstats.client.FSDelayIndexes]] instances. */
object FSDelayIndexes {
  def apply(appId: String, appKey: String): FSDelayIndexes = {
    new FSDelayIndexes(appId, appKey) with FSClientReboot
  }
}

/** A client for FlightStats Delay Index API
  *
  * Use companion object to obtain an instance:
  *
  * {{{
  *   val delayIndexClient = FSDelayIndexes("my app id", "my app key")
  * }}}
  *
  * @see <a target="_top" href="https://developer.flightstats.com/api-docs/delayindex/v1">FlightStats Delay Index API Documentation</a>
  */
abstract class FSDelayIndexes(protected val appId: String, protected val appKey: String) extends FSClientBase {
  // https://api.flightstats.com/flex/delayindex/rest/v1/json
  protected def api = fsHost / "flex" / "delayindex" / "rest" / "v1" / "json"

  /** Delay Indexes by Airport
    *
    * /v1/json/airports/{airports} GET
    */
  def byAirport(airport: String, args: ArgMap = Map.empty): Future[FSDelayIndexResponse] =
    delays(api / "airports" / airport <<? args)

  /** Delay Indexes by Airport
    *
    * /v1/json/airports/{airports} GET
    */    // airports: String* would be cool except then args would have to be the first param...
  def byAirports(airports: Seq[String], args: ArgMap = Map.empty): Future[FSDelayIndexResponse] =
    delays(api / "airports" / airports.mkString(",") <<? args)

  /** Delay Indexes by Country code
    *
    * /v1/json/country/{country} GET
    */
  def byCountry(code: String, args: ArgMap = Map.empty): Future[FSDelayIndexResponse] =
    delays(api / "country" / code <<? args)

  /** Delay Indexes by Region
    *
    * /v1/json/region/{region} GET
    */
  def byRegion(region: String, args: ArgMap = Map.empty): Future[FSDelayIndexResponse] =
    delays(api / "region" / region <<? args)

  /** Delay Indexes by State code (US and Canada only)
    *
    * /v1/json/state/{state} GET
    */
  def byState(code: String, args: ArgMap = Map.empty): Future[FSDelayIndexResponse] =
    delays(api / "state" / code <<? args)


  private def delays(url: RequestBuilder) =
    for ( r <- getAndDeserialize(classOf[FSDelayIndexResponse], url) ) yield r

}
