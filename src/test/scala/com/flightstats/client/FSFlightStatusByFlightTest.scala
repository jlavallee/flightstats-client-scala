package com.flightstats.client

import dispatch.Promise
import org.junit.Test
import org.junit.Assert._
import com.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}
import org.joda.time.DateTime

class FSFlightStatusByFlightTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-14T21:12:23.048-08:00")

  def statuses = FSTestClients.flightStatusByFlight

  @Test def flightStatus =
    checkFlightStatus(statuses.flightStatus(285645279))

  @Test def flightStatusDepartingOnDate =
    checkFlightStatuses(statuses.flightStatusDepartingOnDate("AA","100", date))

  @Test def flightStatusArrivingOnDate =
    checkFlightStatuses(statuses.flightStatusArrivingOnDate("AA","100", date))


  @Test def flightTrack =
    checkFlightTrack(statuses.flightTrack(285645279))

  @Test def flightTracksDepartingOnDate =
    checkFlightTracks(statuses.flightTracksDepartingOnDate("AA", "100", date))

  @Test def flightTracksArrivingOnDate =
    checkFlightTracks(statuses.flightTracksArrivingOnDate("AA", "100", date))

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