package com.flightstats.client

import org.scalatest.Assertions
import org.junit.Test
import org.junit.Before
import com.flightstats.api.v1.FSAirport
import org.joda.time.DateTime
import dispatch.Promise
import com.flightstats.api.v1.alerts.FSAlert

class FSAlertsTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-07T21:12:23.048-08:00")

  val alerts: FSAlerts =
    (appId, appKey) match {
      case (Some(id), Some(key)) =>
        new FSAlerts(id, key) with FSTestRun
      case (_, _) =>
        new FSAlerts("mockId", "mockKey") with FSMockClient
    }

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

  @Test def delete =
    checkAlertRequest( alerts.delete(119281340) )

  @Test def get =
    checkAlertRequest( alerts.get(119281424) )

  def checkAlertRequest(request: Promise[Either[Throwable, FSAlert]]) {
    debug(request)
    request() match {
      case Left(exception) => fail(exception)
      case Right(request) => {
          assert(request != null)
          assert(request.rule.name != null)
      }
    }
  }
}