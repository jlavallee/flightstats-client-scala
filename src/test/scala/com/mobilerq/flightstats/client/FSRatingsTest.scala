package com.mobilerq.flightstats.client

import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Success, Failure}
import org.junit.Test
import org.junit.Assert._
import org.joda.time.DateTime
import com.mobilerq.flightstats.api.v1.ratings.{FSRating, FSRatingsForFlight, FSRatingsForRoute}

class FSRatingsTest extends FSTest {
  val ratings = FSTestClients.ratings

  @Test def factory: Unit = FSRatings("id", "key") match {
    case o: FSRatings => Unit // what we expect
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def forFlight =
    checkRatingsResponse(ratings.forFlight("AA", "100"))

  @Test def forRoute =
    checkRatingsResponse(ratings.forRoute("PDX", "JFK"))


  def checkRatingsResponse(ratingsResponse: Future[AnyRef]) {
    debug(ratingsResponse)
    ratingsResponse onComplete {
      case Failure(exception) => fail(exception.getMessage())
      case Success(FSRatingsForRoute(req, appendix, ratings)) => checkRatings(ratings)
      case Success(FSRatingsForFlight(req, appendix, ratings)) => checkRatings(ratings)
      case x => fail("Whoops, got unexpected response " + x)
    }
    Await.ready(ratingsResponse, duration)
  }

  def checkRatings(ratings: Seq[FSRating]) {
    assertNotNull(ratings)
    assertTrue(ratings.length > 0)
  }
}