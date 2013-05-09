package com.zeroclue.flightstats.client

import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Success, Failure}
import org.junit.Test
import org.junit.Assert._
import com.zeroclue.flightstats.api.v1.delayindex.FSDelayIndexResponse
import com.google.common.cache.CacheBuilder

class FSDelayIndexesTest extends FSTest {

  val delayIndexes = FSTestClients.delayIndexes

  @Test def factory: Unit = FSDelayIndexes("id", "key") match {
    case o: FSDelayIndexes => Unit // what we expect
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def factoryWithCaching: Unit = FSDelayIndexes("id", "key", CacheBuilder.newBuilder()) match {
    case o: FSDelayIndexes with FSCaching => Unit
    case x => fail("didn't get what we expected: " + x)
  }

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

  def checkAirportDelays(future: Future[FSDelayIndexResponse]) {
    debug(future)

    assertNotNull(Await.result(future, duration))
  }

}