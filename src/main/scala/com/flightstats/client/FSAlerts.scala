package com.flightstats.client

import dispatch._
import org.joda.time.DateTime
import com.ning.http.client.RequestBuilder
import com.flightstats.api.v1.alerts.FSAlert

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
    alert( url / "create" / carrier / flightNumber / "to" / arrivalAirport / "arriving" / date
        <<? Map("deliverTo" -> deliverTo, "type" -> "JSON"))

  /* Create flight rule by departure
   * /v1/json/create/{carrier}/{flightNumber}/from/{departureAirport}/departing/{year}/{month}/{day} GET
   */
  def createRuleByDeparture(deliverTo: String, carrier: String, flightNumber: String, departureAirport: String, date: DateTime) =
    alert( url / "create" / carrier / flightNumber / "from" / departureAirport / "departing" / date
        <<? Map("deliverTo" -> deliverTo, "type" -> "JSON"))

  /* Create flight rule for route with arrival date
   * /v1/json/create/{carrier}/{flightNumber}/from/{departureAirport}/to/{arrivalAirport}/arriving/{year}/{month}/{day} GET
   */
  def createRuleForRouteByArrivalDate(deliverTo: String, carrier: String, flightNumber: String, departureAirport: String, arrivalAirport: String, date: DateTime) =
    alert( url / "create" / carrier / flightNumber / "from" / departureAirport / "to" / arrivalAirport / "arriving" / date
        <<? Map("deliverTo" -> deliverTo, "type" -> "JSON"))

  /* Create flight rule for route with departure date
   * /v1/json/create/{carrier}/{flightNumber}/from/{departureAirport}/to/{arrivalAirport}/departing/{year}/{month}/{day} GET
   */
  def createRuleForRouteByDepartureDate(deliverTo: String, carrier: String, flightNumber: String, departureAirport: String, arrivalAirport: String, date: DateTime) =
    alert( url / "create" / carrier / flightNumber / "from" / departureAirport / "to" / arrivalAirport / "departing" / date
        <<? Map("deliverTo" -> deliverTo, "type" -> "JSON"))

  /* Deletes a previously registered rule by ID
   * /v1/json/delete/{ruleId} GET
   */
  def delete(ruleId: Integer) =
    alert(url / "delete" / ruleId.toString())

  /* Retrieve a previously registered rule by ID
   * /v1/json/get/{ruleId} GET
   */
  def get(ruleId: Integer) =
    alert(url / "get" / ruleId.toString())


  private def alert(url: RequestBuilder) =
    for ( r <- getAndDeserialize(classOf[FSAlert], url).right ) yield r

}