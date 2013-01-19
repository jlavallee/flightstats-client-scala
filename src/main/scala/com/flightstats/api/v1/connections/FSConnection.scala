package com.flightstats.api.v1.connections

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import com.flightstats.api.v1.{FSAppendix, FSRequested, FSRequestedAirport, FSRequestedAirline, FSRequestedDate, FSAirport, FSAirline, FSEquipment}
import com.flightstats.api.v1.ratings.FSRating

@JsonCreator
case class FSConnectionsResponse (
  @JsonProperty("request") request: FSConnectionsRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("flights") flights: Seq[FSFlight]
)

@JsonCreator
case class FSFlight (
  @JsonProperty("departureAirport") departureAirport: Option[FSAirport],
  @JsonProperty("departureAirportFsCode") departureAirportFsCode: Option[String],
  @JsonProperty("arrivalAirport") arrivalAirport: Option[FSAirport],
  @JsonProperty("arrivalAirportFsCode") arrivalAirportFsCode: Option[String],
  @JsonProperty("departureDateFrom") departureDateFrom: String,
  @JsonProperty("departureDateTo") departureDateTo: String,
  @JsonProperty("departureDaysOfWeek") departureDaysOfWeek: Seq[Integer],
  @JsonProperty("arrivalDateAdjustment") arrivalDateAdjustment: Integer,
  @JsonProperty("departureTime") departureTime: String,
  @JsonProperty("arrivalTime") arrivalTime: String,
  @JsonProperty("distanceMiles") distanceMiles: Integer,
  @JsonProperty("flightDurationMinutes") flightDurationMinutes: Integer,
  @JsonProperty("layoverDurationMinutes") layoverDurationMinutes: Integer,
  @JsonProperty("flightType") flightType: String, // TODO: create FSFlightType
  @JsonProperty("serviceType") serviceType: String, // TODO: create FSServiceType
  @JsonProperty("online") online: Boolean,
  @JsonProperty("flightLegs") flightLegs: Seq[FSFlightLeg]
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

@JsonCreator
case class FSFlightId (
  @JsonProperty("carrier") carrier: Option[FSAirline],
  @JsonProperty("carrierFsCode") carrierFsCode: Option[String],
  @JsonProperty("flightNumber") flightNumber: Option[String]
)

@JsonCreator
case class FSConnectionsRequest (
  @JsonProperty("airportOrMetro") airportOrMetro: FSRequestedAirportOrMetro,
  @JsonProperty("date") date: FSRequestedDate,
  @JsonProperty("carriers") carriers: Seq[FSRequestedAirline],
  @JsonProperty("codeshareType") codeshareType: FSRequested[String],
  @JsonProperty("serviceType") serviceType: FSRequested[String],
  @JsonProperty("flightType") flightType: FSRequested[String],
  @JsonProperty("codeType") codeType: FSRequested[String],
  @JsonProperty("extendedOptions") extendedOptions: FSRequested[String]
)

@JsonCreator
case class FSRequestedAirportOrMetro (
  @JsonProperty("airport") airport: FSRequestedAirport,
  @JsonProperty("metro") metro: FSRequested[String]
)
