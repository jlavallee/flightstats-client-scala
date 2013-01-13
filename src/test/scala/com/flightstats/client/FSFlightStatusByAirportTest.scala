package com.flightstats.client

import dispatch.Promise
import org.junit.Test
import com.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}
import com.flightstats.client.FSTestUtil._
import org.joda.time.DateTime

class FSFlightStatusByAirportTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-12T21:12:23.048-08:00")

  def statuses = FSTestClients.flightStatusByAirport

  @Test def departureStatus =
    checkFlightStatuses(statuses.departureStatus("PDX", date))

  @Test def arrivalStatus =
    checkFlightStatuses(statuses.arrivalStatus("PDX", date))

  @Test def departureTracks =
    checkFlightTracks(statuses.departureTracks("PDX"))

  @Test def arrivalTracks =
    checkFlightTracks(statuses.arrivalTracks("PDX"))

}