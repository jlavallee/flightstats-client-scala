package com.mobilerq.flightstats.client

import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Success, Failure}
import org.junit.Test
import org.junit.Assert._
import com.mobilerq.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}
import org.joda.time.DateTime
import com.mobilerq.flightstats.api.v1.flightstatus.FSFlightsNearBoundingBox
import com.mobilerq.flightstats.api.v1.flightstatus.FSFlightsNearPointAndDistance
import com.mobilerq.flightstats.api.v1.flightstatus.FSFlightPosition

class FSFlightsNearTest extends FSTest {
  def flightsNear = FSTestClients.flightsNear

  @Test def factory: Unit = FSFlightsNear("id", "key") match {
    case o: FSFlightsNear => Unit // what we expect
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def boundingBox =
    checkFlightsNear(flightsNear.boundingBox(45.0, -125.0, 40.0, -120.0))

  @Test def pointAndDistance =
    checkFlightsNear(flightsNear.pointAndDistance(45.000, -122.00, 25))

  def checkFlightsNear(flightsNear: Future[AnyRef]) {
    debug(flightsNear)

    flightsNear onComplete {
      case Failure(exception) => fail(exception.getMessage)
      case Success(x) => {
        exerciseCaseClass(x)
        x match {
          case FSFlightsNearBoundingBox(req, appendix, flightPositions) => checkFlightPositions(flightPositions)
          case FSFlightsNearPointAndDistance(req, appendix, flightPositions) => checkFlightPositions(flightPositions)
          case x => fail("didn't expect " + x.toString)
        }
      }
      case x => fail("didn't expect " + x.toString)
    }
  }

  def checkFlightPositions(flightPositions: Seq[FSFlightPosition]) {
    for(flightPosition <- flightPositions) {

      assertNotNull(flightPosition)
      assertNotNull(flightPosition.flightId)
      assertNotNull(flightPosition.heading)
      assertTrue(flightPosition.positions.length > 0)
    }
  }

}