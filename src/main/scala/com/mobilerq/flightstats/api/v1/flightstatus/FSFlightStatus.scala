package com.mobilerq.flightstats.api.v1.flightstatus

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import com.mobilerq.flightstats.api.v1._

case class FSFlightStatusResponse (
  request: FSFlightStatusRequest,
  appendix: FSAppendix,
  flightStatus: FSFlightStatus
)

case class FSFlightStatusesResponse (
  request: FSFlightStatusRequest,
  appendix: FSAppendix,
  flightStatuses: Seq[FSFlightStatus]
)

case class FSFlightStatusRequest (
  airline: Option[FSRequestedAirline],
  flight: Option[FSRequested[String]],
  date: Option[FSRequestedDate],
  utc: Option[FSRequested[String]],
  airport: Option[FSRequestedAirport],
  codeType: Option[FSRequested[String]],
  extendedOptions: FSRequested[String]
)

case class FSFlightStatus (
  flightId: Long,
  carrierFsCode: Option[String],
  flightNumber: String,
  departureAirportFsCode: Option[String],
  arrivalAirportFsCode: Option[String],
  departureDate: FSDate,
  arrivalDate: FSDate,
  status: String,
  schedule: FSSchedule,
  operationalTimes: FSOperationalTimes,
  codeshares: Option[Seq[FSCodeshare]],
  flightDurations: FSFlightDuration,
  airportResources: FSAirportResources,
  flightEquipment: FSFlightEquipment
)

class RichFSFlightStatusResponse(response: FSFlightStatusResponse)
  extends FSFlightStatusResponse(response.request, response.appendix, response.flightStatus) {

  override val flightStatus = new RichFSFlightStatus(response.flightStatus, response.appendix)
}

class RichFSFlightStatusesResponse(response: FSFlightStatusesResponse)
  extends FSFlightStatusesResponse(response.request, response.appendix, response.flightStatuses) {

  override val flightStatuses = response.flightStatuses map { new RichFSFlightStatus(_, response.appendix) }
}

class RichFSFlightStatus(status: FSFlightStatus, val appendix: FSAppendix)
  extends FSFlightStatus(  // gosh this is nasty, is there a better way?
      status.flightId, status.carrierFsCode, status.flightNumber,
      status.departureAirportFsCode, status.arrivalAirportFsCode,
      status.departureDate, status.arrivalDate, status.status,
      status.schedule, status.operationalTimes, status.codeshares,
      status.flightDurations, status.airportResources, status.flightEquipment)
  with FlightAppendixHelper
  with CarrierAppendixHelper {
  override val codeshares = status.codeshares map { _ map { new RichFSCodeshare(_, appendix)} }
}
