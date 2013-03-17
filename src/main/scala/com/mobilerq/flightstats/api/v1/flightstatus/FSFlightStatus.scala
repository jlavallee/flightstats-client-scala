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
