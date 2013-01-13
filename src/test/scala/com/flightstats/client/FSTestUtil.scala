package com.flightstats.client

import dispatch.Promise
import org.junit.Assert._
import com.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}

object FSTestUtil extends FSTest {
  def checkFlightStatus(statusPromise: Promise[FSFlightStatusResponse]) {
    val status = statusPromise.either
    debug(status)
    status() match {
      case Left(exception) => fail(exception.getMessage())
      case Right(status) => {
          assertNotNull(status)
          assertNotNull(status.flightStatus)
      }
    }
  }

  def checkFlightStatuses(statusesPromise: Promise[FSFlightStatusesResponse]) {
    val statuses = statusesPromise.either
    debug(statuses)
    statuses() match {
      case Left(exception) => fail(exception.getMessage())
      case Right(statuses) => {
          assertTrue(statuses.flightStatuses.length > 0)
      }
    }
  }

  def checkFlightTrack(trackPromise: Promise[FSFlightTrackResponse]) {
    val track = trackPromise.either
    debug(track)
    track() match {
      case Left(exception) => fail(exception.getMessage())
      case Right(track) => {
          assertNotNull(track)
          assertNotNull(track.flightTrack)
      }
    }
  }

  def checkFlightTracks(tracksPromise: Promise[FSFlightTracksResponse]) {
    val tracks = tracksPromise.either
    debug(tracks)
    tracks() match {
      case Left(exception) => fail(exception.getMessage())
      case Right(tracks) => {
          assertTrue(tracks.flightTracks.length > 0)
      }
    }
  }
}