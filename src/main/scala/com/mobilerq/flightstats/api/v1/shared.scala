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
  flightType: Option[String],
  serviceClasses: Option[String],
  restrictions: Option[String],
  uplines: Option[Seq[FSUplineFlight]],
  downlines: Option[Seq[FSDownlineFlight]]
)

case class FSUplineFlight (
  departureAirport: Option[FSAirport],
  fsCode: String,
  flightId: Long
)

case class FSDownlineFlight (
  arrivalAirport: Option[FSAirport],
  fsCode: String,
  flightId: Long
)

case class FSOperationalTimes (
  publishedDeparture: Option[FSDate],
  publishedArrival: Option[FSDate],
  scheduledGateDeparture: Option[FSDate],
  estimatedGateDeparture: Option[FSDate],
  actualGateDeparture: Option[FSDate],
  flightPlanPlannedDeparture: Option[FSDate],
  estimatedRunwayDeparture: Option[FSDate],
  actualRunwayDeparture: Option[FSDate],
  scheduledGateArrival: Option[FSDate],
  estimatedGateArrival: Option[FSDate],
  actualGateArrival: Option[FSDate],
  flightPlanPlannedArrival: Option[FSDate],
  estimatedRunwayArrival: Option[FSDate],
  actualRunwayArrival: Option[FSDate]
)

case class FSCodeshare (
  carrier: Option[FSAirline],
  fsCode: Option[String],
  flightNumber: Option[String],
  relationship: Option[String]
)

case class FSFlightDuration (
  scheduledBlockMinutes: Option[Integer],
  blockMinutes: Option[Integer],
  scheduledAirMinutes: Option[Integer],
  airMinutes: Option[Integer],
  scheduledTaxiOutMinutes: Option[Integer],
  taxiOutMinutes: Option[Integer],
  scheduledTaxiInMinutes: Option[Integer],
  taxiInMinutes: Option[Integer]
)

case class FSFlightEquipment (
  scheduledEquipmentIataCode: Option[String],
  actualEquipmentIataCode: Option[String],
  tailNumber: Option[String]
)

case class FSAirportResources (
  departureTerminal: Option[String],
  departureGate: Option[String],
  arrivalTerminal: Option[String],
  arrivalGate: Option[String],
  baggage: Option[String]
)

case class FSPosition (
  lon: BigDecimal,
  lat: BigDecimal,
  speedMph: Option[Integer],
  altitudeFt: Option[Integer],
  source: Option[String],
  date: DateTime
)
