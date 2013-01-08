package com.flightstats.client

import org.scalatest.Assertions
import org.junit.Test
import org.junit.Before
import com.flightstats.api.v1.FSAirport
import org.joda.time.DateTime
import dispatch.Promise

class FSAirportsTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-05T21:12:23.048-08:00")

  val airports = FSTestClients.airports

  @Test def active =
    checkAirportList(airports.active)

  @Test def activeOnDate =
    checkAirportList(airports.activeOnDate(date))

  @Test def all =
    checkAirportList(airports.all)

  @Test def byCode =
    checkAirport(airports.byCode("PDX"), "PDX")

  @Test def onDateByCode =
    checkAirport(airports.onDateByCode(date, "PDX"), "PDX")

  @Test def byCityCode =
    checkAirportList(airports.byCityCode("ABC"))

  @Test def byCountryCode =
    checkAirportList(airports.byCountryCode("FR"))

  @Test def byFlightStatsCode =
    checkAirport(airports.byFlightStatsCode("PDX"), "PDX")

  @Test def byIataCode =
    checkAirportList(airports.byIataCode("PDX"))

  @Test def byIataCodeOnDate =
    checkAirport(airports.byIataCodeOnDate("PDX", date), "PDX")

  @Test def byIcaoCode =
    checkAirportList(airports.byIcaoCode("KPDX"))

  @Test def byIcaoCodeOnDate =
    checkAirport(airports.byIcaoCodeOnDate("KPDX", date), "PDX")

  @Test def withinRadius =
    checkAirportList(airports.withinRadius(-122, 45, 50))


  def checkAirportList(airportListPromise: Promise[Seq[FSAirport]]) {
    val airportList = airportListPromise.either
    debug(airportList)
    airportList() match {
      case Left(exception) => fail(exception)
      case Right(list) => {
          assert(list != null)
          assert(list.length > 0)
      }
    }
  }

  def checkAirport(airportPromise: Promise[FSAirport], code: String) {
    val airport = airportPromise.either
    debug(airport)
    airport() match {
      case Left(exception) => fail(exception)
      case Right(airport) => {
          assert(airport != null)
          assert(airport.fs == code)
      }
    }
  }
}