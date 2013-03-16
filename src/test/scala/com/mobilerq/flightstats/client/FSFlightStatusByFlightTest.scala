package com.mobilerq.flightstats.client

import dispatch.Promise
import org.junit.Test
import org.junit.Assert._
import com.mobilerq.flightstats.api.v1.flightstatus._
import com.mobilerq.flightstats.client.FSTestUtil._
import org.joda.time.DateTime

class FSFlightStatusByFlightTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-14T21:12:23.048-08:00")

  def statuses = FSTestClients.flightStatusByFlight

  @Test def factory: Unit = FSFlightStatusByFlight("id", "key") match {
    case o: FSFlightStatusByFlight => Unit // what we expect
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def flightStatus =
    checkFlightStatus(statuses.flightStatus(285645279))

  @Test def flightStatusRich {
    val response: Promise[RichFlightStatusResponse] = statuses.flightStatus(285645279)
    checkFlightStatus(response)
    val flightStatus = response().flightStatus
    assertEquals(Some("AA"), flightStatus.carrier flatMap { _.iata })
    assertEquals(Some("LHR"), flightStatus.arrivalAirport flatMap { _.iata })
    assertEquals(Some("JFK"), flightStatus.departureAirport flatMap { _.iata })
  }

  @Test def flightStatusDepartingOnDate =
    checkFlightStatuses(statuses.flightStatusDepartingOnDate("AA","100", date))

  @Test def flightStatusDepartingOnDateRich {
    val response: Promise[RichFlightStatusesResponse] = statuses.flightStatusDepartingOnDate("AA","100", date)

    checkFlightStatuses(response)
    response().flightStatuses.foreach({ s =>
      assertEquals(Some("AA"), s.carrier flatMap { _.iata })
    })
  }

  @Test def flightStatusArrivingOnDate =
    checkFlightStatuses(statuses.flightStatusArrivingOnDate("AA","100", date))


  @Test def flightTrack =
    checkFlightTrack(statuses.flightTrack(285645279))

  @Test def flightTrackRich = {
    val response: Promise[RichFlightTrackResponse] = statuses.flightTrack(285645279)
    checkFlightTrack(response)
    val flightTrack = response().flightTrack
    assertEquals(Some("AA"), flightTrack.carrier flatMap { _.iata })
    assertEquals(Some("LHR"), flightTrack.arrivalAirport flatMap { _.iata })
    assertEquals(Some("JFK"), flightTrack.departureAirport flatMap { _.iata })
  }

  @Test def flightTracksDepartingOnDate =
    checkFlightTracks(statuses.flightTracksDepartingOnDate("AA", "100", date))

  @Test def flightTracksDepartingOnDateRich {
    val response: Promise[RichFlightTracksResponse] = statuses.flightTracksDepartingOnDate("AA", "100", date)

    checkFlightTracks(response)
    response().flightTracks.foreach({ t =>
      assertEquals(Some("AA"), t.carrier flatMap {_.iata })
    })
  }

  @Test def flightTracksArrivingOnDate =
    checkFlightTracks(statuses.flightTracksArrivingOnDate("AA", "100", date))
}