package com.flightstats.api.v1.flightstatus

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import com.flightstats.api.v1._

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
  airline: FSRequestedAirline,
  flight: FSRequested[String],
  date: FSRequestedDate,
  utc: FSRequested[String],
  airport: FSRequestedAirport,
  codeType: FSRequested[String],
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
  codeshares: Seq[FSCodeShare],
  flightDurations: FSFlightDuration,
  airportResources: FSAirportResources,
  flightEquipment: FSFlightEquipment
)