package com.mobilerq.flightstats.api.v1.flightstatus

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import java.math.BigDecimal
import com.mobilerq.flightstats.api.v1.{FSAppendix, FSRequested, FSPosition}
import org.joda.time.DateTime
import java.net.URL

case class FSFlightsNearBoundingBox (
  request: FSFlightsNearBoundingBoxRequest,
  appendix: FSAppendix,
  flightPositions: Seq[FSFlightPosition]
)

case class FSFlightsNearBoundingBoxRequest (
  topLatitude: FSRequested[BigDecimal],
  leftLongitude: FSRequested[BigDecimal],
  bottomLatitude: FSRequested[BigDecimal],
  rightLongitude: FSRequested[BigDecimal],
  extendedOptions: FSRequested[String],
  url: Option[URL]
)

case class FSFlightsNearPointAndDistance (
  request: FSFlightsNearPointAndDistanceRequest,
  appendix: FSAppendix,
  flightPositions: Seq[FSFlightPosition]
)

case class FSFlightsNearPointAndDistanceRequest (
  latitude: FSRequested[BigDecimal],
  longitude: FSRequested[BigDecimal],
  radiusMiles: FSRequested[Integer],
  maxFlights: FSRequested[Integer],
  extendedOptions: FSRequested[String],
  url: Option[URL]
)

case class FSFlightPosition (
  flightId: Long,
  callsign: String,
  heading: BigDecimal,
  source: String,
  positions: Seq[FSPosition]
)