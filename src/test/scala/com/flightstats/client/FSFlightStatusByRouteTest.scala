package com.flightstats.client

import dispatch.Promise
import org.junit.Test
import com.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}
import com.flightstats.client.FSTestUtil._
import org.joda.time.DateTime

class FSFlightStatusByRouteTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-13T21:12:23.048-08:00")

  def statuses = FSTestClients.flightStatusByRoute

  @Test def departureDate =
    checkFlightStatuses(statuses.departureDate("PDX", "JFK", date))

  @Test def arrivalDate =
    checkFlightStatuses(statuses.arrivalDate("JFK", "PDX", date))

}