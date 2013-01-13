package com.flightstats.client

import dispatch.Promise
import org.junit.Test
import org.junit.Assert._
import org.joda.time.DateTime
import com.flightstats.api.v1.ratings.{FSRating, FSRatingsForFlight, FSRatingsForRoute}

class FSRatingsTest extends FSTest {
  val ratings = FSTestClients.ratings

  @Test def forFlight =
    checkRatingsResponse(ratings.forFlight("AA", "100"))

  @Test def forRoute =
    checkRatingsResponse(ratings.forRoute("PDX", "JFK"))


  def checkRatingsResponse(ratingsPromise: Promise[AnyRef]) {
    val ratingsResponse = ratingsPromise.either
    debug(ratingsResponse)
    ratingsResponse() match {
      case Left(exception) => fail(exception.getMessage())
      case Right(FSRatingsForRoute(req, appendix, ratings)) => checkRatings(ratings)
      case Right(FSRatingsForFlight(req, appendix, ratings)) => checkRatings(ratings)
      case x => fail("Whoops, got unexpected response " + x)
    }
  }

  def checkRatings(ratings: Seq[FSRating]) {
    assertNotNull(ratings)
    assertTrue(ratings.length > 0)
  }
}