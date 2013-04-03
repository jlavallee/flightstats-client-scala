package com.mobilerq.flightstats.api.v1.connections

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import com.mobilerq.flightstats.api.v1._
import com.mobilerq.flightstats.api.v1.ratings.FSRating

case class FSConnectionsResponse (
  request: FSConnectionsRequest,
  appendix: FSAppendix,
  flights: Seq[FSFlight]
)

case class FSFlight (
  departureAirportFsCode: Option[String],
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
  flightType: String,  // TODO: create FSFlightType enum
  serviceType: String, // TODO: create FSServiceType enum
  online: Boolean,
  flightLegs: Seq[FSFlightLeg]
)

@JsonCreator
class FSFlightLeg (
  @JsonProperty("departureAirportFsCode") val departureAirportFsCode: Option[String],
  @JsonProperty("arrivalAirportFsCode") val arrivalAirportFsCode: Option[String],
  @JsonProperty("departureTime") val departureTime: String,
  @JsonProperty("arrivalTime") val arrivalTime: String,
  @JsonProperty("departureDateAdjustment") val departureDateAdjustment: Integer,
  @JsonProperty("arrivalDateAdjustment") val arrivalDateAdjustment: Integer,
  @JsonProperty("departureTerminal") val departureTerminal: Option[String],
  @JsonProperty("arrivalTerminal") val arrivalTerminal: Option[String],
  @JsonProperty("carrierFsCode") val carrierFsCode: Option[String],
  @JsonProperty("flightNumber") val flightNumber: String,
  @JsonProperty("wetleaseInfo") val wetleaseInfo: Option[String],
  @JsonProperty("codeshare") val codeshare: Boolean,
  @JsonProperty("operator") val operator: Option[FSFlightId],
  @JsonProperty("codeshares") val codeshares: Option[Seq[FSFlightId]],
  @JsonProperty("stops") val stops: Seq[FSAirport],
  @JsonProperty("stopCodes") val stopCodes: Seq[String],
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

class RichConnectionsResponse(r: FSConnectionsResponse)
  extends FSConnectionsResponse(r.request, r.appendix, r.flights) {
  override val flights = r.flights map { new RichFlight(_, r.appendix)}
}

class RichFlight(f: FSFlight, val appendix: FSAppendix)
  extends FSFlight(
    f.departureAirportFsCode,
    f.arrivalAirportFsCode,
    f.departureDateFrom,
    f.departureDateTo,
    f.departureDaysOfWeek,
    f.arrivalDateAdjustment,
    f.departureTime,
    f.arrivalTime,
    f.distanceMiles,
    f.flightDurationMinutes,
    f.layoverDurationMinutes,
    f.flightType,
    f.serviceType,
    f.online,
    f.flightLegs
  )
  with FlightAppendixHelper {
  override val flightLegs = f.flightLegs map { new RichFlightLeg(_, appendix) }
}

class RichFlightLeg(fl: FSFlightLeg, val appendix: FSAppendix)
  extends FSFlightLeg(
    fl.departureAirportFsCode,
    fl.arrivalAirportFsCode,
    fl.departureTime,
    fl.arrivalTime,
    fl.departureDateAdjustment,
    fl.arrivalDateAdjustment,
    fl.departureTerminal,
    fl.arrivalTerminal,
    fl.carrierFsCode,
    fl.flightNumber,
    fl.wetleaseInfo,
    fl.codeshare,
    fl.operator,
    fl.codeshares,
    fl.stops,
    fl.stopCodes,
    fl.equipmentCodes,
    fl.distanceMiles,
    fl.flightDurationMinutes,
    fl.layoverDurationMinutes,
    fl.ratings
  )
  with FlightAppendixHelper
  with CarrierAppendixHelper
  with EquipmentAppendixHelper
  // it would be nice to handle codeshares, they're different types than FSCodeshare though