package com.zeroclue.flightstats.client

import org.junit.Test
import org.junit.Assert._
import com.zeroclue.flightstats.api.v1.flightstatus.{FSFlightStatusResponse, FSFlightStatusesResponse, FSFlightTrackResponse, FSFlightTracksResponse}
import com.zeroclue.flightstats.client.FSTestUtil._
import org.joda.time.DateTime
import com.google.common.cache.CacheBuilder

class FSFlightStatusByRouteTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-13T21:12:23.048-08:00")

  def statuses = FSTestClients.flightStatusByRoute

  @Test def factory: Unit = FSFlightStatusByRoute("id", "key") match {
    case o: FSFlightStatusByRoute => Unit // what we expect
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def factoryWithCaching: Unit = FSFlightStatusByRoute("id", "key", CacheBuilder.newBuilder()) match {
    case o: FSFlightStatusByRoute with FSCaching => Unit
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def departureDate =
    checkFlightStatuses(statuses.departureDate("PDX", "JFK", date))

  @Test def arrivalDate =
    checkFlightStatuses(statuses.arrivalDate("JFK", "PDX", date))

}
