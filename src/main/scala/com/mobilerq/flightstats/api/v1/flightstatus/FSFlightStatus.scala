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
  carrierFsCode: String,
  flightNumber: String,
  departureAirportFsCode: String,
  arrivalAirportFsCode: String,
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

class RichFlightStatusResponse(response: FSFlightStatusResponse)
  extends FSFlightStatusResponse(response.request, response.appendix, response.flightStatus) {

  override val flightStatus = new RichFlightStatus(response.flightStatus, response.appendix)
}

class RichFlightStatusesResponse(response: FSFlightStatusesResponse)
  extends FSFlightStatusesResponse(response.request, response.appendix, response.flightStatuses) {

  override val flightStatuses = response.flightStatuses map { new RichFlightStatus(_, response.appendix) }
}

class RichFlightStatus(status: FSFlightStatus, val appendix: FSAppendix)
  extends FSFlightStatus(  // gosh this is nasty, is there a better way?
      status.flightId, status.carrierFsCode, status.flightNumber,
      status.departureAirportFsCode, status.arrivalAirportFsCode,
      status.departureDate, status.arrivalDate, status.status,
      status.schedule, status.operationalTimes, status.codeshares,
      status.flightDurations, status.airportResources, status.flightEquipment)
  with FlightAppendixHelper {
  override val codeshares = status.codeshares map { _ map { new RichCodeshare(_, appendix)} }
}

class RichCodeshare(cs: FSCodeshare, val appendix: FSAppendix)
  extends FSCodeshare(cs.fsCode, cs.flightNumber, cs.relationship) {
  def carrier: Option[FSAirline] = fsCode match {
    case None => None
    case Some(fsCode) => appendix.airlinesMap.get(fsCode)
  }
}
