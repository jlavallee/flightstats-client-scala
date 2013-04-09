package com.mobilerq.flightstats.client

import org.junit.Test
import org.junit.Assert._
import org.joda.time.DateTime
import com.mobilerq.flightstats.api.v1.FSAirport
import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Success, Failure}
import com.google.common.cache.CacheBuilder

class FSAirportsTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-05T21:12:23.048-08:00")

  val airports = FSTestClients.airports

  @Test def factory: Unit = FSAirports("id", "key") match {
    case o: FSAirports => Unit // what we expect
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def factoryWithCaching: Unit = FSAirports("id", "key", CacheBuilder.newBuilder()) match {
    case o: FSAirports with FSCaching => Unit
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def active =
    checkAirportList(airports.active())

  @Test def activeOnDate =
    checkAirportList(airports.activeOnDate(date))

  @Test def all =
    checkAirportList(airports.all())

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


  def checkAirportList(future: Future[Seq[FSAirport]]) {
    debug(future)
    val airportList = Await.result(future, duration)
    assertNotNull(airportList)
    assertTrue(airportList.length > 0)
    airportList.foreach{ exerciseCaseClass(_) }
  }

  def checkAirport(future: Future[FSAirport], code: String) {
    debug(future)
    val airport = Await.result(future, duration)
    assertNotNull(airport)
    exerciseCaseClass(airport)
    assertEquals(code, airport.fs)
  }
}
