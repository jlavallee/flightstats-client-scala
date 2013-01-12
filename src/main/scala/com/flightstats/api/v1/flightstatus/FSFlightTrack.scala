package com.flightstats.api.v1.flightstatus

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import com.flightstats.api.v1._

@JsonCreator
case class FSFlightTrackResponse (
  @JsonProperty("request") request: FSFlightTrackRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("flightTrack") flightTrack: FSFlightTrack
)

@JsonCreator
case class FSFlightTracksResponse (
  @JsonProperty("request") request: FSFlightTrackRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("flightTracks") flightTracks: Seq[FSFlightTrack]
)

@JsonCreator
case class FSFlightTrackRequest (
  // WSDL specifies all of these as optional...
  @JsonProperty("flightId") flightId: FSRequested[Long],
  @JsonProperty("date") date: FSRequestedDate,
  //@JsonProperty("hourOfDay") hourOfDay: FSRequested[Integer],
  //@JsonProperty("numHours") numHours: FSRequested[Integer],
  @JsonProperty("includeFlightPlan") includeFlightPlan: FSRequested[Boolean],
  @JsonProperty("maxPositions") maxPositions: FSRequested[Integer],
  @JsonProperty("maxPositionAgeMinutes") maxPositionAgeMinutes: FSRequested[Integer],
  @JsonProperty("extendedOptions") extendedOptions: FSRequested[String]
)

@JsonCreator
case class FSFlightTrack (
  @JsonProperty("flightId") flightId: Long,
  @JsonProperty("carrierFsCode") carrierFsCode: String,
  @JsonProperty("flightNumber") flightNumber: String,
  @JsonProperty("departureAirportFsCode") departureAirportFsCode: String,
  @JsonProperty("arrivalAirportFsCode") arrivalAirportFsCode: String,
  @JsonProperty("departureDate") departureDate: FSDate,
  @JsonProperty("equipment") equipment: String,
  @JsonProperty("bearing") bearing: BigDecimal,
  @JsonProperty("positions") positions: Seq[FSPosition],
  @JsonProperty("waypoints") waypoints: Seq[FSWaypoint]
)

@JsonCreator
case class FSPosition (
  @JsonProperty("lon") lon: BigDecimal,
  @JsonProperty("lat") lat: BigDecimal,
  @JsonProperty("speedMph") speedMph: Option[Integer],
  @JsonProperty("altitudeFt") altitudeFt: Option[Integer],
  @JsonProperty("source") source: Option[String],
  @JsonProperty("date") date: String
)

@JsonCreator
case class FSWaypoint (
  @JsonProperty("lon") lon: BigDecimal,
  @JsonProperty("lat") lat: BigDecimal
)
