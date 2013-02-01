package com.mobilerq.flightstats.api.v1

import org.joda.time.DateTime
import java.util.UUID
import java.math.BigDecimal
import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

case class FSAirline(
  fs: String,  // "AA"
  iata: Option[String], // "AA"
  icao: Option[String], // "AAL"
  name: String, // "American Airlines"
  phoneNumber: Option[String], // "1-800-433-7300"
  active: Boolean,
  dateFrom: Option[DateTime],
  dateTo: Option[DateTime]
)

case class FSAirportsHolder(
  airports: Seq[FSAirport]
)

case class FSAirportHolder(
  airport: FSAirport
)

@JsonCreator
class FSAirport (  // too many parameters for a case class...
  @JsonProperty("fs") val fs: String,
  @JsonProperty("iata") val iata: Option[String],
  @JsonProperty("icao") val icao: Option[String],
  @JsonProperty("faa") val faa: Option[String],
  @JsonProperty("name") val name: Option[String],
  @JsonProperty("street1") val street1: Option[String],
  @JsonProperty("street2") val street2: Option[String],
  @JsonProperty("city") val city: String,
  @JsonProperty("cityCode") val cityCode: Option[String],
  @JsonProperty("district") val district: Option[String],
  @JsonProperty("stateCode") val stateCode: Option[String],
  @JsonProperty("postalCode") val postalCode: Option[String],
  @JsonProperty("countryCode") val countryCode: String,
  @JsonProperty("countryName") val countryName: String,
  @JsonProperty("regionName") val regionName: String,
  @JsonProperty("timeZoneRegionName") val timeZoneRegionName: String,
  @JsonProperty("weatherZone") val weatherZone: Option[String],
  @JsonProperty("localTime") val localTime: String,
  @JsonProperty("utcOffsetHours") val utcOffsetHours: BigDecimal,
  @JsonProperty("latitude") val latitude: BigDecimal,
  @JsonProperty("longitude") val longitude: BigDecimal,
  @JsonProperty("elevationFeet") val elevationFeet: Option[Integer],
  @JsonProperty("classification") val classification: Integer,
  @JsonProperty("active") val active: Boolean,
  @JsonProperty("dateFrom") val dateFrom: Option[String],
  @JsonProperty("dateTo") val dateTo: Option[String],
  @JsonProperty("delayIndexUrl") val delayIndexUrl: Option[String],
  @JsonProperty("weatherUrl") val weatherUrl: Option[String]
)


case class FSAppendix (
  airports: Option[Seq[FSAirport]],
  airlines: Option[Seq[FSAirline]],
  equipment: Option[Seq[FSEquipment]]
)

case class FSEquipment (
    iata: String,
    name: String,
    turboProp: Boolean,
    jet: Boolean,
    widebody: Boolean,
    regional: Boolean
)

case class FSDate (
  dateLocal: DateTime,  // should get no timezone!
  dateUtc: DateTime
)


case class FSError(
  httpStatusCode: Integer,
  errorCode: String, // TODO: make an enum for these
  errorId: UUID,
  errorMessage: String
)

case class FSRequested[T] (
  requested: Option[String],
  interpreted: Option[T],
  error: Option[FSError]
)

case class FSRequestedDate(
  year: String,
  month: String,
  day: String,
  interpreted: String,
  error: Option[String]
)

case class FSRequestedAirport(
  requestedCode: Option[String],
  fsCode: Option[String],
  error: Option[FSError]
)

case class FSRequestedAirline (
  requestedCode: Option[String],
  fsCode: Option[String],
  error: Option[FSError]
)


case class FSSchedule (
  flightType: String,
  serviceClasses: String,
  restrictions: String,
  uplines: Seq[FSUplineFlight],
  downlines: Seq[FSDownlineFlight]
)

case class FSUplineFlight (
  departureAirport: FSAirport,
  fsCode: String,
  flightId: Long
)

case class FSDownlineFlight (
  arrivalAirport: FSAirport,
  fsCode: String,
  flightId: Long
)

case class FSOperationalTimes (
  publishedDeparture: FSDate,
  publishedArrival: FSDate,
  scheduledGateDeparture: FSDate,
  estimatedGateDeparture: FSDate,
  actualGateDeparture: FSDate,
  flightPlanPlannedDeparture: FSDate,
  estimatedRunwayDeparture: FSDate,
  actualRunwayDeparture: FSDate,
  scheduledGateArrival: FSDate,
  estimatedGateArrival: FSDate,
  actualGateArrival: FSDate,
  flightPlanPlannedArrival: FSDate,
  estimatedRunwayArrival: FSDate,
  actualRunwayArrival: FSDate
)

case class FSCodeShare (
  carrier: Option[FSAirport],
  fsCode: String,
  flightNumber: String,
  relationship: String
)

case class FSFlightDuration (
  scheduledBlockMinutes: Integer,
  blockMinutes: Integer,
  scheduledAirMinutes: Integer,
  airMinutes: Integer,
  scheduledTaxiOutMinutes: Integer,
  taxiOutMinutes: Integer,
  scheduledTaxiInMinutes: Integer,
  taxiInMinutes: Integer
)

case class FSFlightEquipment (
  scheduledEquipment: FSEquipment,
  scheduledEquipmentIataCode: String,
  actualEquipment: FSEquipment,
  actualEquipmentIataCode: String,
  tailNumber: String
)

case class FSAirportResources (
  departureTerminal: String,
  departureGate: String,
  arrivalTerminal: String,
  arrivalGate: String,
  baggage: String
)

case class FSPosition (
  lon: BigDecimal,
  lat: BigDecimal,
  speedMph: Option[Integer],
  altitudeFt: Option[Integer],
  source: Option[String],
  date: DateTime
)