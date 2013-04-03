package com.mobilerq.flightstats.client

import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Success, Failure}
import org.junit.Test
import org.junit.Assert._
import org.joda.time.DateTime
import com.mobilerq.flightstats.api.v1.ratings._

class FSRatingsTest extends FSTest {
  val ratings = FSTestClients.ratings

  @Test def factory: Unit = FSRatings("id", "key") match {
    case o: FSRatings => Unit // what we expect
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def forFlight =
    checkRatingsResponse(ratings.forFlight("AA", "100"))

  @Test def forFlightRich = {
    val r: RichFSRatingsForFlight = Await.result(ratings.forFlight("AA", "100"), duration)
    assertEquals(Some("John F. Kennedy International Airport"),
        r.ratings(0).departureAirport flatMap {_.name})
    assertEquals(Some("American Airlines"), r.ratings(0).airline map {_.name})
  }

  @Test def forRoute =
    checkRatingsResponse(ratings.forRoute("PDX", "JFK"))

  @Test def forRouteRich = {
    val r: RichFSRatingsForRoute = Await.result(ratings.forRoute("PDX", "JFK"), duration)
    assertEquals(Some("Portland International Airport"),
        r.ratings(0).departureAirport flatMap {_.name})
    assertEquals(Some("Air France"), r.ratings(0).airline map {_.name})
  }

  def checkRatingsResponse(ratingsResponse: Future[AnyRef]) {
    debug(ratingsResponse)
    Await.result(ratingsResponse, duration) match {
      case Failure(exception) => fail(exception.getMessage())
      case FSRatingsForRoute(req, appendix, ratings) => checkRatings(ratings)
      case FSRatingsForFlight(req, appendix, ratings) => checkRatings(ratings)
      case x => fail("Whoops, got unexpected response " + x)
    }
  }

  def checkRatings(ratings: Seq[FSRating]) {
    assertNotNull(ratings)
    assertTrue(ratings.length > 0)
  }
}