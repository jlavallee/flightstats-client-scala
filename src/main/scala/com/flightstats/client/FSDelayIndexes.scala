package com.flightstats.client

import com.ning.http.client.RequestBuilder
import dispatch._
import com.flightstats.api.v1.delayindex.FSDelayIndexResponse

object FSDelayIndexes {
  def apply(appId: String, appKey: String): FSDelayIndexes = {
    new FSDelayIndexes(appId, appKey) with FSClientReboot with JacksonMapper
  }
}
abstract class FSDelayIndexes(val appId: String, val appKey: String) extends FSClient {
  // https://api.flightstats.com/flex/delayindex/rest/v1/json
  def apiLocation = Seq("flex", "delayindex", "rest", "v1", "json")
  /* Delay Indexes by Airport
   * /v1/json/airports/{airports} GET
   */
  def byAirport(airports: String*): Promise[FSDelayIndexResponse] =
    delays(url / "airports" / airports.foldLeft("")(_ + "," + _))

  /* Delay Indexes by Country code
   * /v1/json/country/{country} GET
   */
  def byCountry(code: String): Promise[FSDelayIndexResponse] =
    delays(url / "country" / code)

  /* Delay Indexes by Region
   * /v1/json/region/{region} GET
   */
  def byRegion(region: String): Promise[FSDelayIndexResponse] =
    delays(url / "region" / region)

  /* Delay Indexes by State code (US and Canada only)
   * /v1/json/state/{state} GET
   */
  def byState(code: String): Promise[FSDelayIndexResponse] =
    delays(url / "state" / code)


  private def delays(url: RequestBuilder) =
    for ( r <- getAndDeserialize(classOf[FSDelayIndexResponse], url) ) yield r

}
