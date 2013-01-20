package com.flightstats.client

import dispatch.Promise
import org.junit.Test
import org.junit.Assert._
import com.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}
import org.joda.time.DateTime
import com.flightstats.api.v1.flightstatus.FSFlightsNearBoundingBox
import com.flightstats.api.v1.flightstatus.FSFlightsNearPointAndDistance
import com.flightstats.api.v1.flightstatus.FSFlightPosition

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

  def checkFlightsNear(promise: Promise[AnyRef]) {
    val either = promise.either

    debug(either)

    either() match {
      case Left(exception) => fail(exception.getMessage)
      case Right(x) => {
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