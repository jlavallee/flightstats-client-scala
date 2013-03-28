package com.mobilerq.flightstats.client

import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Success, Failure}
import org.junit.Test
import org.junit.Assert._
import org.joda.time.DateTime

class FSAlertsTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-14T21:12:23.048-08:00")

  val alerts = FSTestClients.alerts

  @Test def factory: Unit = FSAlerts("id", "key") match {
    case o: FSAlerts => Unit // what we expect
    case x => fail("didn't get what we expected: " + x)
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

  // this test will fail on capture - first we need to actually create a rule!
  @Test def delete =
    checkAlertRequest( alerts.delete(119281424) )

  // this test will pass misleadingly on capture - we need to creat a rule to get
  @Test def get =
    checkAlertRequest( alerts.get(119281424) )

  def checkAlertRequest(alertPromise: Future[AnyRef]) {
    import ExecutionContext.Implicits.global
    val alert = alertPromise
    debug(alert)
    
    alert onComplete {
      case Failure(exception) => fail(exception.getMessage())
      case Success(result) => {
          assertNotNull(result)
          exerciseCaseClass(result)
      }
    }
    Await.result(alert, duration)
  }
}