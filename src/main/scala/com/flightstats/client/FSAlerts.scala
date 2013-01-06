package com.flightstats.client

import dispatch._
import com.ning.http.client.RequestBuilder
import com.flightstats.api.v1.FSAirport
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.flightstats.api.v1.FSAirportsHolder
import org.joda.time.DateTime
import com.flightstats.api.v1.FSAirportHolder
import com.flightstats.api.v1.alerts.FSAlertRequestHolder

object FSAlerts {
  def apply(appId: String, appKey: String): FSAlerts = {
    new FSAlerts(appId, appKey) with FSClientReboot with JacksonMapper
  }
}

abstract class FSAlerts(val appId: String, val appKey: String) extends FSClient {

  // https://api.flightstats.com/flex/alerts/rest/v1/json
  def apiLocation = Seq("flex", "alerts", "rest", "v1", "json")

  /* Create flight rule by arrival
   * /v1/json/create/{carrier}/{flightNumber}/to/{arrivalAirport}/arriving/{year}/{month}/{day} GET
   */
  def createRuleByArrival(deliverTo: String, carrier: String, flightNumber: String, arrivalAirport: String, date: DateTime) =
    // TODO: allow type to be configurable
    createAlert( url / "create" / carrier / flightNumber / "to" / arrivalAirport / "arriving" / date,
        Map("deliverTo" -> deliverTo, "type" -> "JSON"))

  /* Create flight rule by departure
   * /v1/json/create/{carrier}/{flightNumber}/from/{departureAirport}/departing/{year}/{month}/{day} GET
   */
  def createRuleByDeparture(deliverTo: String, carrier: String, flightNumber: String, departureAirport: String, date: DateTime) =
    createAlert( url / "create" / carrier / flightNumber / "from" / departureAirport / "departing" / date,
        Map("deliverTo" -> deliverTo, "type" -> "JSON"))

  /* Create flight rule for route with arrival date
   * /v1/json/create/{carrier}/{flightNumber}/from/{departureAirport}/to/{arrivalAirport}/arriving/{year}/{month}/{day} GET
   */

  /* Create flight rule for route with departure date
   * /v1/json/create/{carrier}/{flightNumber}/from/{departureAirport}/to/{arrivalAirport}/departing/{year}/{month}/{day} GET
   */

  /* Deletes a previously registered rule by ID
   * /v1/json/delete/{ruleId} GET
   */

  /* Retrieve a previously registered rule by ID
   * /v1/json/get/{ruleId} GET
   */



  private def createAlert(url: RequestBuilder, createArgs: Map[String, String]) =
    for ( r <- getAndDeserialize(classOf[FSAlertRequestHolder], url).right ) yield r.request

}
