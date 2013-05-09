package com.zeroclue.flightstats.client

import org.junit.Test
import org.junit.Assert._
import com.zeroclue.flightstats.api.v1.flightstatus._
import com.zeroclue.flightstats.client.FSTestUtil._
import org.joda.time.DateTime
import scala.concurrent.{Await, Future}
import com.google.common.cache.CacheBuilder

class FSFlightStatusByAirportTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-12T21:12:23.048-08:00")

  def statuses = FSTestClients.flightStatusByAirport

  @Test def factory: Unit = FSFlightStatusByAirport("id", "key") match {
    case o: FSFlightStatusByAirport => Unit // what we expect
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def factoryWithCaching: Unit = FSFlightStatusByAirport("id", "key", CacheBuilder.newBuilder()) match {
    case o: FSFlightStatusByAirport with FSCaching => Unit
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def departureStatus =
    checkFlightStatuses(statuses.departureStatus("PDX", date))

  @Test def departureStatusRich {
    val response: Future[RichFSFlightStatusesResponse] = statuses.departureStatus("PDX", date)

    Await.result(response, duration).flightStatuses.foreach({ s => assertEquals(Some("PDX"), s.departureAirport flatMap { _.iata }) })
  }

  @Test def arrivalStatus =
    checkFlightStatuses(statuses.arrivalStatus("PDX", date))

  @Test def departureTracks =
    checkFlightTracks(statuses.departureTracks("PDX"))

  @Test def arrivalTracks =
    checkFlightTracks(statuses.arrivalTracks("PDX"))

}