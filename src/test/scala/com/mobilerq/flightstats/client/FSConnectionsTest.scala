package com.mobilerq.flightstats.client

import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Success, Failure}
import org.junit.Test
import org.junit.Assert._
import org.joda.time.DateTime
import com.mobilerq.flightstats.api.v1.connections._
import com.google.common.cache.CacheBuilder

class FSConnectionsTest extends FSTest {
  val date: DateTime = DateTime.parse("2013-01-22T00:00:00.000Z")
  val connections = FSTestClients.connections

  @Test def factory: Unit = FSConnections("id", "key") match {
    case o: FSConnections => Unit // what we expect
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def factoryWithCaching: Unit = FSConnections("id", "key", CacheBuilder.newBuilder()) match {
    case o: FSConnections with FSCaching => Unit
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def directByArrivalAndDate =
    checkConnectionsResponse(connections.directByArrivalAndDate("PDX", date))

  @Test def directByArrivalAndDateRich = {
    val c: RichFSConnectionsResponse = Await.result(connections.directByArrivalAndDate("PDX", date), duration)
    assertEquals(Some("Portland International Airport"),
        c.flights(0).arrivalAirport flatMap {_.name})
    assertEquals(Some("Portland International Airport"),
        c.flights(0).flightLegs(0).arrivalAirport flatMap {_.name})
    assertEquals(true,
        c.flights(0).flightLegs(0).equipments(0).jet)
    assertEquals("Boeing (Douglas) MD-90",
        c.flights(0).flightLegs(0).equipments(0).name)
  }

  @Test def directByDepartureAndDate =
    checkConnectionsResponse(connections.directByDepartureAndDate("JFK", date))

  @Test def directByCarrierAndFlightNumberAndArrivalDate =
    checkConnectionsResponse(connections.directByCarrierAndFlightNumberAndArrivalDate("AA", "100", date))

  @Test def directByCarrierAndFlightNumberAndArrivalLocationAndDate =
    checkConnectionsResponse(connections.directByCarrierAndFlightNumberAndArrivalLocationAndDate("AA", "100", "LHR", date))

  @Test def directByCarrierAndFlightNumberAndDepartureDate =
    checkConnectionsResponse(connections.directByCarrierAndFlightNumberAndDepartureDate("AA", "100", date))

  @Test def directByCarrierAndFlightNumberAndDepartureLocationAndDate =
    checkConnectionsResponse(connections.directByCarrierAndFlightNumberAndDepartureLocationAndDate("AA", "100", "JFK", date))

  @Test def betweenLocationsByArrivalDate =
    checkConnectionsResponse(connections.betweenLocationsByArrivalDate("PDX", "JFK", date))

  @Test def betweenLocationsByDepartureDate =
    checkConnectionsResponse(connections.betweenLocationsByDepartureDate("PDX", "JFK", date))


  def checkConnectionsResponse(connectionsResponse: Future[FSConnectionsResponse]) {
    debug(connectionsResponse)

    checkConnections(Await.result(connectionsResponse, duration))
  }

  def checkConnections(connections: FSConnectionsResponse) {
    assertNotNull(connections)
    exerciseCaseClass(connections)
  }
}