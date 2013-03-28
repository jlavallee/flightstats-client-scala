package com.mobilerq.flightstats.client

import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Success, Failure}
import org.junit.Assert._
import com.mobilerq.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}

object FSTestUtil extends FSTest {
  import ExecutionContext.Implicits.global

  def checkFlightStatus(status: Future[FSFlightStatusResponse]) {
    debug(status)
    status onComplete {
      case Failure(exception) => fail(exception.getMessage())
      case Success(status) => {
        exerciseCaseClass(status)
        assertNotNull(status)
        assertNotNull(status.flightStatus)
      }
    }
    Await.result(status, duration)
  }

  def checkFlightStatuses(statuses: Future[FSFlightStatusesResponse]) {
    debug(statuses)
    statuses onComplete {
      case Failure(exception) => fail(exception.getMessage())
      case Success(statuses) => {
        statuses.flightStatuses.foreach(exerciseCaseClass(_))
        assertTrue(statuses.flightStatuses.length > 0)
      }
    }
    Await.result(statuses, duration)
  }

  def checkFlightTrack(track: Future[FSFlightTrackResponse]) {
    debug(track)
    track onComplete {
      case Failure(exception) => fail(exception.getMessage())
      case Success(track) => {
        exerciseCaseClass(track)
        assertNotNull(track)
        assertNotNull(track.flightTrack)
      }
    }
    Await.result(track, duration)
  }

  def checkFlightTracks(tracks: Future[FSFlightTracksResponse]) {
    debug(tracks)
    tracks onComplete {
      case Failure(exception) => fail(exception.getMessage())
      case Success(tracks) => {
        tracks.flightTracks.foreach(exerciseCaseClass(_))
        assertTrue(tracks.flightTracks.length > 0)
      }
    }
    Await.result(tracks, duration)
  }
}