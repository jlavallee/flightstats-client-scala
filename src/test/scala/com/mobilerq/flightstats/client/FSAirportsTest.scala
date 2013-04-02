package com.mobilerq.flightstats.client

import org.junit.Test
import org.junit.Assert._
import org.joda.time.DateTime
import com.mobilerq.flightstats.api.v1.FSAirport
import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Success, Failure}

class FSAirportsTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-05T21:12:23.048-08:00")

  val airports = FSTestClients.airports

  @Test def factory: Unit = FSAirports("id", "key") match {
    case o: FSAirports => Unit // what we expect
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


  def checkAirportList(airportList: Future[Seq[FSAirport]]) {
    debug(airportList)
    airportList onComplete {
      case Failure(exception) => fail(exception.getMessage())
      case Success(list) => {
          assertNotNull(list)
          assertTrue(list.length > 0)
          list.foreach{ exerciseCaseClass(_) }
      }
    }
    Await.result(airportList, duration)
  }

  def checkAirport(airport: Future[FSAirport], code: String) {
    debug(airport)
    airport onComplete {
      case Failure(exception) => fail(exception.getMessage())
      case Success(airport) => {
          assertNotNull(airport)
          exerciseCaseClass(airport)
          assertEquals(code, airport.fs)
      }
    }
    Await.result(airport, duration)
  }
}
