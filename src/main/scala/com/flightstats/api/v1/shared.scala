package com.flightstats.api.v1

import org.joda.time.DateTime
import java.util.UUID
import java.math.BigDecimal
import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

@JsonCreator
case class FSAirline(
  @JsonProperty("active") active: String, // true
  @JsonProperty("phoneNumber") phoneNumber: String, // "1-800-433-7300"
  @JsonProperty("name") name: String, // "American Airlines"
  @JsonProperty("icao") icao: String, // "AAL"
  @JsonProperty("iata") iata: String, // "AA"
  @JsonProperty("fs") fs: String  // "AA"
)

@JsonCreator
case class FSAirportsHolder(
  @JsonProperty("airports") airports: Seq[FSAirport]
)

@JsonCreator
case class FSAirportHolder(
  @JsonProperty("airport") airport: FSAirport
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


@JsonCreator
case class FSAppendix (
  @JsonProperty("airports") airports: Option[Seq[FSAirport]],
  @JsonProperty("airlines") airlines: Option[Seq[FSAirline]],
  @JsonProperty("equipments") equipment: Option[Seq[FSEquipment]]
)

@JsonCreator
case class FSEquipment (
    @JsonProperty("iata") iata: String,
    @JsonProperty("name") name: String,
    @JsonProperty("turboProp") turboProp: Boolean,
    @JsonProperty("jet") jet: Boolean,
    @JsonProperty("widebody") widebody: Boolean,
    @JsonProperty("regional") regional: Boolean
)

case class FSDate (
  @JsonProperty("dateLocal") dateLocal: DateTime,  // should get no timezone!
  @JsonProperty("dateUtc") dateUtc: DateTime
)


@JsonCreator
case class FSError(
  @JsonProperty("httpStatusCode") httpStatusCode: Integer,
  @JsonProperty("errorCode") errorCode: String, // TODO: make an enum for these
  @JsonProperty("errorId") errorId: UUID,
  @JsonProperty("errorMessage") errorMessage: String
)

@JsonCreator
case class FSRequested[T] (
  @JsonProperty("requested") requested: Option[String],
  @JsonProperty("interpreted") interpreted: Option[T],
  @JsonProperty("error") error: Option[FSError]
)

@JsonCreator
case class FSRequestedDate(
  @JsonProperty("year") year: String,
  @JsonProperty("month") month: String,
  @JsonProperty("day") day: String,
  @JsonProperty("interpreted") interpreted: String,
  @JsonProperty("error") error: Option[String]
)

@JsonCreator
case class FSRequestedAirport(
  @JsonProperty("requestedCode") requestedCode: String,
  @JsonProperty("fsCode") fsCode: String,
  @JsonProperty("error") error: Option[FSError]
)

@JsonCreator
case class FSRequestedAirline (
  @JsonProperty("requestedCode") reqeustedCode: String,
  @JsonProperty("fsCode") fsCode: String,
  @JsonProperty("error") error: Option[FSError]
)


@JsonCreator
case class FSSchedule (
  @JsonProperty("flightType") flightType: String,
  @JsonProperty("serviceClasses") serviceClasses: String,
  @JsonProperty("restrictions") restrictions: String,
  @JsonProperty("uplines") uplines: Seq[FSUplineFlight],
  @JsonProperty("downlines") downlines: Seq[FSDownlineFlight]
)

@JsonCreator
case class FSUplineFlight (
  @JsonProperty("departureAirport") departureAirport: FSAirport,
  @JsonProperty("fsCode") fsCode: String,
  @JsonProperty("flightId") flightId: Long
)

@JsonCreator
case class FSDownlineFlight (
  @JsonProperty("arrivalAirport") arrivalAirport: FSAirport,
  @JsonProperty("fsCode") fsCode: String,
  @JsonProperty("flightId") flightId: Long
)

@JsonCreator
case class FSOperationalTimes (
  @JsonProperty("publishedDeparture") publishedDeparture: FSDate,
  @JsonProperty("publishedArrival") publishedArrival: FSDate,
  @JsonProperty("scheduledGateDeparture") scheduledGateDeparture: FSDate,
  @JsonProperty("estimatedGateDeparture") estimatedGateDeparture: FSDate,
  @JsonProperty("actualGateDeparture") actualGateDeparture: FSDate,
  @JsonProperty("flightPlanPlannedDeparture") flightPlanPlannedDeparture: FSDate,
  @JsonProperty("estimatedRunwayDeparture") estimatedRunwayDeparture: FSDate,
  @JsonProperty("actualRunwayDeparture") actualRunwayDeparture: FSDate,
  @JsonProperty("scheduledGateArrival") scheduledGateArrival: FSDate,
  @JsonProperty("estimatedGateArrival") estimatedGateArrival: FSDate,
  @JsonProperty("actualGateArrival") actualGateArrival: FSDate,
  @JsonProperty("flightPlanPlannedArrival") flightPlanPlannedArrival: FSDate,
  @JsonProperty("estimatedRunwayArrival") estimatedRunwayArrival: FSDate,
  @JsonProperty("actualRunwayArrival") actualRunwayArrival: FSDate
)

@JsonCreator
case class FSCodeShare (
  @JsonProperty("carrier") carrier: Option[FSAirport],
  @JsonProperty("fsCode") fsCode: String,
  @JsonProperty("flightNumber") flightNumber: String,
  @JsonProperty("relationship") relationship: String
)

@JsonCreator
case class FSFlightDuration (
  @JsonProperty("scheduledBlockMinutes") scheduledBlockMinutes: Integer,
  @JsonProperty("blockMinutes") blockMinutes: Integer,
  @JsonProperty("scheduledAirMinutes") scheduledAirMinutes: Integer,
  @JsonProperty("airMinutes") airMinutes: Integer,
  @JsonProperty("scheduledTaxiOutMinutes") scheduledTaxiOutMinutes: Integer,
  @JsonProperty("taxiOutMinutes") taxiOutMinutes: Integer,
  @JsonProperty("scheduledTaxiInMinutes") scheduledTaxiInMinutes: Integer,
  @JsonProperty("taxiInMinutes") taxiInMinutes: Integer
)

@JsonCreator
case class FSFlightEquipment (
  @JsonProperty("scheduledEquipment") scheduledEquipment: FSEquipment,
  @JsonProperty("scheduledEquipmentIataCode") scheduledEquipmentIataCode: String,
  @JsonProperty("actualEquipment") actualEquipment: FSEquipment,
  @JsonProperty("actualEquipmentIataCode") actualEquipmentIataCode: String,
  @JsonProperty("tailNumber") tailNumber: String
)

@JsonCreator
case class FSAirportResources (
  @JsonProperty("departureTerminal") departureTerminal: String,
  @JsonProperty("departureGate") departureGate: String,
  @JsonProperty("arrivalTerminal") arrivalTerminal: String,
  @JsonProperty("arrivalGate") arrivalGate: String,
  @JsonProperty("baggage") baggage: String
)

@JsonCreator
case class FSPosition (
  @JsonProperty("lon") lon: BigDecimal,
  @JsonProperty("lat") lat: BigDecimal,
  @JsonProperty("speedMph") speedMph: Option[Integer],
  @JsonProperty("altitudeFt") altitudeFt: Option[Integer],
  @JsonProperty("source") source: Option[String],
  @JsonProperty("date") date: DateTime
)