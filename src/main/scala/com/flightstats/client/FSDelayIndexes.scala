package com.flightstats.client

import com.ning.http.client.RequestBuilder
import dispatch._
import com.flightstats.api.v1.delayindex.FSDelayIndexResponse

object FSDelayIndexes {
  def apply(appId: String, appKey: String): FSDelayIndexes = {
    new FSDelayIndexes(appId, appKey) with FSClientReboot with JacksonMapper
  }
}
abstract class FSDelayIndexes(val appId: String, val appKey: String) extends FSClientBase {

  // https://api.flightstats.com/flex/delayindex/rest/v1/json
  def apiLocation = Seq("flex", "delayindex", "rest", "v1", "json")
  /* Delay Indexes by Airport
   * /v1/json/airports/{airports} GET
   */
  def byAirport(airport: String, args: ArgMap = Map.empty): Promise[FSDelayIndexResponse] =
    delays(fs / "airports" / airport <<? args)

  /* Delay Indexes by Airport
   * /v1/json/airports/{airports} GET
   */    // airports: String* would be cool except then args would have to be the first param...
  def byAirports(airports: Seq[String], args: ArgMap = Map.empty): Promise[FSDelayIndexResponse] =
    delays(fs / "airports" / airports.mkString(",") <<? args)

  /* Delay Indexes by Country code
   * /v1/json/country/{country} GET
   */
  def byCountry(code: String, args: ArgMap = Map.empty): Promise[FSDelayIndexResponse] =
    delays(fs / "country" / code <<? args)

  /* Delay Indexes by Region
   * /v1/json/region/{region} GET
   */
  def byRegion(region: String, args: ArgMap = Map.empty): Promise[FSDelayIndexResponse] =
    delays(fs / "region" / region <<? args)

  /* Delay Indexes by State code (US and Canada only)
   * /v1/json/state/{state} GET
   */
  def byState(code: String, args: ArgMap = Map.empty): Promise[FSDelayIndexResponse] =
    delays(fs / "state" / code <<? args)


  private def delays(url: RequestBuilder) =
    for ( r <- getAndDeserialize(classOf[FSDelayIndexResponse], url) ) yield r

}
