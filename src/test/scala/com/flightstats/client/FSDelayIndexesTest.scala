package com.flightstats.client

import org.junit.Test
import com.flightstats.api.v1.delayindex.FSDelayIndexResponse
import dispatch.Promise

class FSDelayIndexesTest extends FSTest {

  val delayIndexes = FSTestClients.delayIndexes

  @Test def byAirport =
    checkAirportDelays(delayIndexes.byAirport("PDX"))

  @Test def byAirports =
    checkAirportDelays(delayIndexes.byAirports(Seq("PDX", "SEA")))

  @Test def byCountry =
    checkAirportDelays(delayIndexes.byCountry("FR"))

  @Test def byCountryArgs =
    checkAirportDelays(delayIndexes.byCountry("FR", Map("classification" -> "3", "score" -> "2")))

  @Test def byRegion =
    checkAirportDelays(delayIndexes.byRegion("Caribbean"))

  @Test def byRegionArgs =
    checkAirportDelays(delayIndexes.byRegion("Caribbean", Map("classification" -> "3", "score" -> "3")))

  @Test def byState =
    checkAirportDelays(delayIndexes.byState("OR"))

  def checkAirportDelays(delayIndexPromise: Promise[FSDelayIndexResponse]) {
    val delayIndex = delayIndexPromise.either

    debug(delayIndex)

    delayIndex() match {
      case Left(exception) => fail(exception)
      case Right(delayIndex) => {
          assert(delayIndex != null)
      }
    }
  }

}