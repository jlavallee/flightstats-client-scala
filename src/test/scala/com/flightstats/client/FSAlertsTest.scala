package com.flightstats.client

import dispatch.Promise
import org.junit.Test
import org.junit.Assert._
import org.joda.time.DateTime
import com.flightstats.api.v1.alerts.FSAlert

class FSAlertsTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-07T21:12:23.048-08:00")

  val alerts = FSTestClients.alerts

  @Test def createRuleByArrival =
    checkAlertRequest(
        alerts.createRuleByArrival(
            deliverTo = "http://your.post.url",
            carrier = "AA",
            flightNumber = "100",
            arrivalAirport = "LHR",
            date = date
        ))

  @Test def createRuleByDeparture =
    checkAlertRequest(
        alerts.createRuleByDeparture(
            deliverTo = "http://your.post.url",
            carrier = "AA",
            flightNumber = "100",
            departureAirport = "JFK",
            date = date
        ))

  @Test def createRuleForRouteByArrivalDate =
    checkAlertRequest(
        alerts.createRuleForRouteByArrivalDate(
            deliverTo = "http://your.post.url",
            carrier = "AA",
            flightNumber = "100",
            departureAirport = "JFK",
            arrivalAirport = "LHR",
            date = date
        ))

  @Test def createRuleForRouteByDepartureDate =
    checkAlertRequest(
        alerts.createRuleForRouteByDepartureDate(
            deliverTo = "http://your.post.url",
            carrier = "AA",
            flightNumber = "100",
            departureAirport = "JFK",
            arrivalAirport = "LHR",
            date = date
        ))

  // this test will fail on capture - first we need to actually create a rule!
  @Test def delete =
    checkAlertRequest( alerts.delete(119281340) )

  // this test will pass misleadingly on capture - we need to creat a rule to get
  @Test def get =
    checkAlertRequest( alerts.get(119281424) )

  def checkAlertRequest(alertPromise: Promise[FSAlert]) {
    val alert = alertPromise.either
    debug(alert)
    alert() match {
      case Left(exception) => fail(exception.getMessage())
      case Right(request) => {
          assertNotNull(request)
          assertNotNull(request.rule.name)
          assertNotNull(request.appendix.airlines)
          assertNotNull(request.appendix.airports)
      }
    }
  }
}