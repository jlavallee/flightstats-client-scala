package com.flightstats.api.v1.connections

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import com.flightstats.api.v1.{FSAppendix, FSRequested, FSRequestedAirport, FSRequestedAirline, FSRequestedDate, FSAirport, FSAirline, FSEquipment}
import com.flightstats.api.v1.ratings.FSRating

case class FSConnectionsResponse (
  request: FSConnectionsRequest,
  appendix: FSAppendix,
  flights: Seq[FSFlight]
)

case class FSFlight (
  departureAirport: Option[FSAirport],
  departureAirportFsCode: Option[String],
  arrivalAirport: Option[FSAirport],
  arrivalAirportFsCode: Option[String],
  departureDateFrom: String,
  departureDateTo: String,
  departureDaysOfWeek: Seq[Integer],
  arrivalDateAdjustment: Integer,
  departureTime: String,
  arrivalTime: String,
  distanceMiles: Integer,
  flightDurationMinutes: Integer,
  layoverDurationMinutes: Integer,
  flightType: String, // TODO: create FSFlightType
  serviceType: String, // TODO: create FSServiceType
  online: Boolean,
  flightLegs: Seq[FSFlightLeg]
)

@JsonCreator
class FSFlightLeg (
  @JsonProperty("departureAirport") val departureAirport: Option[FSAirport],
  @JsonProperty("departureAirportFsCode") val departureAirportFsCode: Option[String],
  @JsonProperty("arrivalAirport") val arrivalAirport: Option[FSAirport],
  @JsonProperty("arrivalAirportFsCode") val arrivalAirportFsCode: Option[String],
  @JsonProperty("departureTime") val departureTime: String,
  @JsonProperty("arrivalTime") val arrivalTime: String,
  @JsonProperty("departureDateAdjustment") val departureDateAdjustment: Integer,
  @JsonProperty("arrivalDateAdjustment") val arrivalDateAdjustment: Integer,
  @JsonProperty("departureTerminal") val departureTerminal: Option[String],
  @JsonProperty("arrivalTerminal") val arrivalTerminal: Option[String],
  @JsonProperty("carrier") val carrier: Option[FSAirline],
  @JsonProperty("carrierFsCode") val carrierFsCode: Option[String],
  @JsonProperty("flightNumber") val flightNumber: String,
  @JsonProperty("wetleaseInfo") val wetleaseInfo: Option[String],
  @JsonProperty("codeshare") val codeshare: Boolean,
  @JsonProperty("operator") val operator: Option[FSFlightId],
  @JsonProperty("codeshares") val codeshares: Seq[FSFlightId],
  @JsonProperty("stops") val stops: Seq[FSAirport],
  @JsonProperty("stopCodes") val stopCodes: Seq[String],
  @JsonProperty("equipment") val equipment: FSEquipment,
  @JsonProperty("equipmentCodes") val equipmentCodes: Seq[String],
  @JsonProperty("distanceMiles") val distanceMiles: Integer,
  @JsonProperty("flightDurationMinutes") val flightDurationMinutes: Integer,
  @JsonProperty("layoverDurationMinutes") val layoverDurationMinutes: Integer,
  @JsonProperty("ratings") val ratings: Seq[FSRating]
)

case class FSFlightId (
  carrier: Option[FSAirline],
  carrierFsCode: Option[String],
  flightNumber: Option[String]
)

case class FSConnectionsRequest (
  airportOrMetro: Option[FSRequestedAirportOrMetro],
  date: Option[FSRequestedDate],
  carriers: Option[Seq[FSRequestedAirline]],
  codeshareType: Option[FSRequested[String]],
  serviceType: Option[FSRequested[String]],
  flightType: Option[FSRequested[String]],
  codeType: Option[FSRequested[String]],
  extendedOptions: Option[FSRequested[String]]
)

case class FSRequestedAirportOrMetro (
  airport: FSRequestedAirport,
  metro: FSRequested[String]
)
