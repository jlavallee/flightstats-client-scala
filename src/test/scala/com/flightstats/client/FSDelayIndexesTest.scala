package com.flightstats.client

import org.junit.Test
import com.flightstats.api.v1.delayindex.FSDelayIndexResponse
import dispatch.Promise

class FSDelayIndexesTest extends FSTest {

  val delayIndexes = FSTestClients.delayIndexes

  @Test def byAirport =
    checkAirportDelays(delayIndexes.byAirport("PDX"))

  @Test def byCountry =
    checkAirportDelays(delayIndexes.byCountry("FR"))

  @Test def byRegion =
    checkAirportDelays(delayIndexes.byRegion("Caribbean"))

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