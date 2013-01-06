package com.flightstats.client

import org.scalatest.Assertions
import org.junit.Test
import org.junit.Before
import com.flightstats.api.v1.FSAirport
import org.joda.time.DateTime
import dispatch.Promise

class FSAirportsTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-05T21:12:23.048-08:00")

  val airports: FSAirports =
        if(appId.isDefined && appKey.isDefined)
          FSAirports(appId.get, appKey.get)
        else
          new FSAirports("mockId", "mockKey")
            with FSMockClient

  @Test def active {
    checkAirportList(airports.active)
  }

  @Test def activeWithDate {
    checkAirportList(airports.active(date))
  }

  @Test def all {
    checkAirportList(airports.all)
  }
  
  @Test def byCode {
    checkAirport(airports.byCode("PDX"), "PDX")
  }

  @Test def onDateByCode {
    checkAirport(airports.onDateByCode(date, "PDX"), "PDX")
  }

  def checkAirportList(airportList: Promise[Either[Throwable, Seq[FSAirport]]]) = {
    println(airportList)
    for(a <- airportList)
      println(a)  // should be Promise(-incomplete-) when hitting FlightStats

    airportList() match {
      case Left(exception) => fail(exception)
      case Right(list) => {
          assert(list != null)
          assert(list.length > 0)
          println(list(0))
      }
    }
  }

  def checkAirport(airport: Promise[Either[Throwable, FSAirport]], code: String) = {
    println(airport)
    for(a <- airport)
      println(a)  // should be Promise(-incomplete-) when hitting FlightStats

    airport() match {
      case Left(exception) => fail(exception)
      case Right(airport) => {
          assert(airport != null)
          assert(airport.fs == code)
      }
    }
  }

}