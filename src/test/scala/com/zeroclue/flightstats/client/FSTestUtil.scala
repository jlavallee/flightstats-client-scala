package com.zeroclue.flightstats.client

import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Success, Failure}
import org.junit.Assert._
import com.zeroclue.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}

object FSTestUtil extends FSTest {

  def checkFlightStatus(response: Future[FSFlightStatusResponse]) {
    debug(response)
    val status = Await.result(response, duration)
    exerciseCaseClass(status)
    assertNotNull(status)
    assertNotNull(status.flightStatus)
  }

  def checkFlightStatuses(response: Future[FSFlightStatusesResponse]) {
    debug(response)
    val statuses = Await.result(response, duration)
    assertTrue(statuses.flightStatuses.length > 0)
  }

  def checkFlightTrack(response: Future[FSFlightTrackResponse]) {
    debug(response)
    val track = Await.result(response, duration)
    exerciseCaseClass(track)
    assertNotNull(track)
    assertNotNull(track.flightTrack)
  }

  def checkFlightTracks(response: Future[FSFlightTracksResponse]) {
    debug(response)
    val tracks = Await.result(response, duration)
    tracks.flightTracks.foreach(exerciseCaseClass(_))
    assertTrue(tracks.flightTracks.length > 0)
  }
}