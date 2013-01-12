package com.flightstats.api.v1.flightstatus

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import com.flightstats.api.v1._

@JsonCreator
case class FSFlightStatusResponse (
  @JsonProperty("request") request: FSFlightStatusRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("flightStatus") flightStatus: FSFlightStatus
)

@JsonCreator
case class FSFlightStatusesResponse (
  @JsonProperty("request") request: FSFlightStatusRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("flightStatuses") flightStatuses: Seq[FSFlightStatus]
)

@JsonCreator
case class FSFlightStatusRequest (
  @JsonProperty("airline") airline: FSRequestedAirline,
  @JsonProperty("flight") flight: FSRequested[String],
  @JsonProperty("date") date: FSRequestedDate,
  @JsonProperty("utc") utc: FSRequested[String],
  @JsonProperty("airport") airport: FSRequestedAirport,
  @JsonProperty("codeType") codeType: FSRequested[String],
  @JsonProperty("extendedOptions") extendedOptions: FSRequested[String]
)

@JsonCreator
case class FSFlightStatus (
  @JsonProperty("flightId") flightId: Long,
  @JsonProperty("carrierFsCode") carrierFsCode: String,
  @JsonProperty("flightNumber") flightNumber: String,
  @JsonProperty("departureAirportFsCode") departureAirportFsCode: String,
  @JsonProperty("arrivalAirportFsCode") arrivalAirportFsCode: String,
  @JsonProperty("departureDate") departureDate: FSDate,
  @JsonProperty("arrivalDate") arrivalDate: FSDate,
  @JsonProperty("status") status: String,
  @JsonProperty("schedule") schedule: FSSchedule,
  @JsonProperty("operationalTimes") operationalTimes: FSOperationalTimes,
  @JsonProperty("codeshares") codeshares: Seq[FSCodeShare],
  @JsonProperty("flightDurations") flightDurations: FSFlightDuration,
  @JsonProperty("airportResources") airportResources: FSAirportResources,
  @JsonProperty("flightEquipment") flightEquipment: FSFlightEquipment
)