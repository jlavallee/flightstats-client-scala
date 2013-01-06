package com.flightstats.client

import org.scalatest.Assertions
import org.junit.Test
import org.junit.Before
import com.flightstats.api.v1.FSAirport

class FSAirportsTest extends Assertions {

  val appId = sys.props.get("flightstats.appid")
  val appKey = sys.props.get("flightstats.appkey")
  var client: FSAirports =
        if(appId.isDefined && appKey.isDefined)
          FSAirports(appId.get, appKey.get)
        else
          new FSAirports("mockId", "mockKey")
            with FSMockClient

  @Test def activeAirports {
    val airports = client.active
    println(airports)
    for(a <- airports)
      println(a)  // should be Promise(-incomplete-) when hitting FlightStats
    airports() match {
      case Left(exception) => fail(exception)
      case Right(list) => {
          assert(list != null)
          assert(list.length > 0)
          println(list(0))
      }

    }
  }

}