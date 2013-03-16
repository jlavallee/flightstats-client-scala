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

object FSFlightStatus {
  def apply(status: FSFlightStatus) = status
}
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

  
  implicit val impAppendix: FSAppendix = response.appendix
  override val flightStatus = new RichFlightStatus(response.flightStatus)
}

class RichFlightStatus(status: FSFlightStatus)(implicit appendix: FSAppendix)
  extends FSFlightStatus(  // gosh this is nasty, is there a better way?
      status.flightId, status.carrierFsCode, status.flightNumber,
      status.departureAirportFsCode, status.arrivalAirportFsCode,
      status.departureDate, status.arrivalDate, status.status,
      status.schedule, status.operationalTimes, status.codeshares,
      status.flightDurations, status.airportResources, status.flightEquipment) {
  def carrierIataCode: Option[String] =
    appendix.airlinesMap.get(status.carrierFsCode) flatMap { _.iata }

  def arrivalAirportIataCode: Option[String] =
    appendix.airportsMap.get(status.arrivalAirportFsCode) flatMap { _.iata }

  def departureAirportIataCode: Option[String] =
    appendix.airportsMap.get(status.departureAirportFsCode) flatMap { _.iata }

}
