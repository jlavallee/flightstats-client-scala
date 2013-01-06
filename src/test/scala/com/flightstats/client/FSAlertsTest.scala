package com.flightstats.client

import org.scalatest.Assertions
import org.junit.Test
import org.junit.Before
import com.flightstats.api.v1.FSAirport
import org.joda.time.DateTime
import dispatch.Promise
import com.flightstats.api.v1.alerts.FSAlertRequest

class FSAlertsTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-06T21:12:23.048-08:00")

  val alerts: FSAlerts =
        if(appId.isDefined && appKey.isDefined)
          new FSAlerts(appId.get, appKey.get)
            with FSClientReboot
            with JacksonMapper
            with FSTestRun
        else
          new FSAlerts("mockId", "mockKey")
            with FSMockClient

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

  def checkAlertRequest(request: Promise[Either[Throwable, FSAlertRequest]]) {
    debug(request)
    request() match {
      case Left(exception) => fail(exception)
      case Right(request) => {
          assert(request != null)
          //assert(request.rule.name != null)
      }
    }
  }
}