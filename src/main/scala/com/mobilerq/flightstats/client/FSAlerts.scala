package com.mobilerq.flightstats.client

import scala.concurrent.Future
import dispatch._
import dispatch.Defaults.executor
import org.joda.time.DateTime
import com.ning.http.client.RequestBuilder
import com.mobilerq.flightstats.api.v1.alerts.{FSGetAlert, FSCreateAlert}
import com.google.common.cache.CacheBuilder

/** Factory for [[com.mobilerq.flightstats.client.FSAlerts]] instances. */
object FSAlerts {
  def apply(appId: String, appKey: String): FSAlerts = {
    new FSAlerts(appId, appKey) with FSClientReboot
  }

  def apply(appId: String, appKey: String, cacheBuilder: CacheBuilder[Object, Object]) = {
    new FSAlerts(appId, appKey)
      with FSClientReboot
      with FSCaching {
        override val cache = cacheBuilder.build(loader)
    }
  }
}

/** A client for FlightStats Alerts API
  *
  * Use companion object to obtain an instance:
  *
  * {{{
  *   val alertsClient = FSAlerts("my app id", "my app key")
  * }}}
  *
  * @see <a target="_top" href="https://developer.flightstats.com/api-docs/alerts/v1">FlightStats Alerts API Documentation</a>
  */
abstract class FSAlerts(protected val appId: String, protected val appKey: String) extends FSClientBase {
  // https://api.flightstats.com/flex/alerts/rest/v1/json
  protected def api = fsHost / "flex" / "alerts" / "rest" / "v1" / "json"

  /** Create flight rule by arrival
    *
    * /v1/json/create/{carrier}/{flightNumber}/to/{arrivalAirport}/arriving/{year}/{month}/{day} GET
    */
  def createRuleByArrival(deliverTo: String, carrier: String, flightNumber: String, arrivalAirport: String, date: DateTime, args: ArgMap = Map.empty): Future[FSCreateAlert] =
    // TODO: allow type to be configurable
    create( api / "create" / carrier / flightNumber / "to" / arrivalAirport / "arriving" / date
        <<? Map("deliverTo" -> deliverTo, "type" -> "JSON") ++ args)

  /** Create flight rule by departure
    *
    * /v1/json/create/{carrier}/{flightNumber}/from/{departureAirport}/departing/{year}/{month}/{day} GET
    */
  def createRuleByDeparture(deliverTo: String, carrier: String, flightNumber: String, departureAirport: String, date: DateTime, args: ArgMap = Map.empty): Future[FSCreateAlert] =
    create( api / "create" / carrier / flightNumber / "from" / departureAirport / "departing" / date
        <<? Map("deliverTo" -> deliverTo, "type" -> "JSON") ++ args)

  /** Create flight rule for route with arrival date
    *
    * /v1/json/create/{carrier}/{flightNumber}/from/{departureAirport}/to/{arrivalAirport}/arriving/{year}/{month}/{day} GET
    */
  def createRuleForRouteByArrivalDate(deliverTo: String, carrier: String, flightNumber: String, departureAirport: String, arrivalAirport: String, date: DateTime, args: ArgMap = Map.empty): Future[FSCreateAlert] =
    create( api / "create" / carrier / flightNumber / "from" / departureAirport / "to" / arrivalAirport / "arriving" / date
        <<? Map("deliverTo" -> deliverTo, "type" -> "JSON") ++ args)

  /** Create flight rule for route with departure date
    *
    * /v1/json/create/{carrier}/{flightNumber}/from/{departureAirport}/to/{arrivalAirport}/departing/{year}/{month}/{day} GET
    */
  def createRuleForRouteByDepartureDate(deliverTo: String, carrier: String, flightNumber: String, departureAirport: String, arrivalAirport: String, date: DateTime, args: ArgMap = Map.empty): Future[FSCreateAlert] =
    create( api / "create" / carrier / flightNumber / "from" / departureAirport / "to" / arrivalAirport / "departing" / date
        <<? Map("deliverTo" -> deliverTo, "type" -> "JSON") ++ args)

  /** Deletes a previously registered rule by ID
    *
    * /v1/json/delete/{ruleId} GET
    */
  def delete(ruleId: Integer): Future[FSGetAlert] =
    get(api / "delete" / ruleId.toString())

  /** Retrieve a previously registered rule by ID
    *
    * /v1/json/get/{ruleId} GET
    */
  def get(ruleId: Integer): Future[FSGetAlert] =
    get(api / "get" / ruleId.toString())


  private def create(url: RequestBuilder) =
    for ( r <- getAndDeserialize(classOf[FSCreateAlert], url) ) yield r

  private def get(url: RequestBuilder) =
    for ( r <- getAndDeserialize(classOf[FSGetAlert], url) ) yield r
}
