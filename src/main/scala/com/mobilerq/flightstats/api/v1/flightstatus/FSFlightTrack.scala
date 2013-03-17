package com.mobilerq.flightstats.api.v1.flightstatus

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import com.mobilerq.flightstats.api.v1._
import java.net.URL

case class FSFlightTrackResponse (
  request: FSFlightTrackRequest,
  appendix: FSAppendix,
  flightTrack: FSFlightTrack
)

case class FSFlightTracksResponse (
  request: FSFlightTrackRequest,
  appendix: FSAppendix,
  flightTracks: Seq[FSFlightTrack]
)

case class FSFlightTrackRequest (
  // WSDL specifies all of these as optional...
  flightId: FSRequested[Long],
  date: Option[FSRequestedDate],
  hourOfDay: Option[FSRequested[Integer]],
  numHours: Option[FSRequested[Integer]],
  includeFlightPlan: FSRequested[Boolean],
  maxPositions: FSRequested[Integer],
  maxPositionAgeMinutes: FSRequested[Integer],
  extendedOptions: FSRequested[String],
  url: URL
)

case class FSFlightTrack (
  flightId: Long,
  carrierFsCode: String,
  flightNumber: String,
  departureAirportFsCode: String,
  arrivalAirportFsCode: String,
  departureDate: FSDate,
  equipment: String,
  bearing: BigDecimal,
  positions: Seq[FSPosition],
  waypoints: Option[Seq[FSWaypoint]]
)

case class FSWaypoint (
  lon: BigDecimal,
  lat: BigDecimal
)
