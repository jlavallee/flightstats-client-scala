package com.mobilerq.flightstats.client

import org.junit.Test
import org.junit.Assert._
import org.joda.time.DateTime
import com.mobilerq.flightstats.api.v1._
import com.mobilerq.flightstats.api.v1.flightstatus._
import com.mobilerq.flightstats.client.FSTestUtil._
import scala.concurrent.{Await, Future}
import com.google.common.cache.CacheBuilder
import com.ning.http.client.Request

class FSFlightStatusByFlightTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-14T21:12:23.048-08:00")

  def statuses = FSTestClients.flightStatusByFlight

  @Test def factory: Unit = FSFlightStatusByFlight("id", "key") match {
    case o: FSFlightStatusByFlight => Unit // what we expect
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def flightStatus =
    checkFlightStatus(statuses.flightStatus(285645279))

  @Test def flightStatusRichAppendix {

    val response = Await.result(statuses.flightStatus(285645279), duration)
    val status = response.flightStatus
    val appendix = response.appendix
    assertEquals(Some("AA"), status.carrierFsCode flatMap {appendix.airlinesMap.get(_)} flatMap {_.iata})
    assertEquals(Some("LHR"), status.arrivalAirportFsCode flatMap {appendix.airportsMap.get(_)} flatMap {_.iata})
    assertEquals(Some("JFK"), status.departureAirportFsCode flatMap {appendix.airportsMap.get(_)} flatMap {_.iata})
  }
  @Test def flightStatusCaching = {
    val cacheBuilder = CacheBuilder.newBuilder()
                                   .maximumSize(100000)
                                   .recordStats()
    val cachingClient = new FSFlightStatusByFlight("anyId", "anyKey")
                              with FSMockClient
                              with FSCaching {
      override protected val cache = cacheBuilder.build(loader)
    }

    val futureList = Future.sequence((0 until 10).map { x => cachingClient.flightStatus(285645279) })

    Await.ready(futureList, duration)

    //println(cachingClient.cacheStats)

    assertEquals(1, cachingClient.cacheStats.missCount)
    assertEquals(9, cachingClient.cacheStats.hitCount)
    assertEquals(1, cachingClient.cacheStats.loadSuccessCount)
    assertEquals(0, cachingClient.cacheStats.loadExceptionCount)
  }

  @Test def flightStatusRich {
    val response: Future[RichFSFlightStatusResponse] = statuses.flightStatus(285645279)
    checkFlightStatus(response)
    val flightStatus = Await.result(response, duration).flightStatus
    assertEquals(Some("AA"), flightStatus.carrier flatMap { _.iata })
    assertEquals(Some("LHR"), flightStatus.arrivalAirport flatMap { _.iata })
    assertEquals(Some("JFK"), flightStatus.departureAirport flatMap { _.iata })
  }

  @Test def flightStatusDepartingOnDate =
    checkFlightStatuses(statuses.flightStatusDepartingOnDate("AA","100", date))

  @Test def flightStatusDepartingOnDateRich {
    val response: Future[RichFSFlightStatusesResponse] = statuses.flightStatusDepartingOnDate("AA","100", date)

    checkFlightStatuses(response)
    Await.result(response, duration).flightStatuses.foreach({ s =>
      assertEquals(Some("AA"), s.carrier flatMap { _.iata })
    })
  }

  @Test def flightStatusArrivingOnDate =
    checkFlightStatuses(statuses.flightStatusArrivingOnDate("AA","100", date))


  @Test def flightTrack =
    checkFlightTrack(statuses.flightTrack(285645279))

  @Test def flightTrackRich = {
    val response: Future[RichFSFlightTrackResponse] = statuses.flightTrack(285645279)
    checkFlightTrack(response)
    val flightTrack = Await.result(response, duration).flightTrack
    assertEquals(Some("AA"), flightTrack.carrier flatMap { _.iata })
    assertEquals(Some("LHR"), flightTrack.arrivalAirport flatMap { _.iata })
    assertEquals(Some("JFK"), flightTrack.departureAirport flatMap { _.iata })
  }

  @Test def flightTracksDepartingOnDate =
    checkFlightTracks(statuses.flightTracksDepartingOnDate("AA", "100", date))

  @Test def flightTracksDepartingOnDateRich {
    val response: Future[RichFSFlightTracksResponse] = statuses.flightTracksDepartingOnDate("AA", "100", date)

    checkFlightTracks(response)
    Await.result(response, duration).flightTracks.foreach({ t =>
      assertEquals(Some("AA"), t.carrier flatMap {_.iata })
    })
  }

  @Test def flightTracksArrivingOnDate =
    checkFlightTracks(statuses.flightTracksArrivingOnDate("AA", "100", date))
}