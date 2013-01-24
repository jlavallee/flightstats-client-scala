package com.flightstats.api.v1.alerts

import java.net.URL
import org.joda.time.DateTime
import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import com.flightstats.api.v1._

case class FSCreateAlert (
  rule: FSAlertRule,
  alertCapabilities: Option[FSAlertCapabilities],
  appendix: FSAppendix,
  request: FSCreateAlertRequest,
  error: Option[FSError]
)

case class FSGetAlert (
  rule: FSAlertRule,
  alertCapabilities: Option[FSAlertCapabilities],
  appendix: FSAppendix,
  request: FSGetAlertRequest,
  error: Option[FSError]
)

case class FSAlertRule (
  id: String,
  name: String,
  carrierFsCode: String,
  flightNumber: String,
  departure: DateTime,
  departureAirport: Option[String],
  departureAirportFsCode: Option[String],
  arrival: DateTime,
  arrivalAirport: Option[String],
  arrivalAirportFsCode: Option[String],
  nameValues: Seq[FSNameValue],
  ruleEvents: Seq[FSAlertRuleEvent],
  delivery: FSAlertRuleDelivery
)

case class FSNameValue (
  name: String,
  value: String
)

case class FSAlertRuleEvent (
  @JsonProperty("type") eventType : FSAlertRuleEventType
)

case class FSAlertRuleDelivery(
  destination: URL,
  format: String
)

case class FSAlertCapabilities(
  runwayArrival: Boolean,
  runwayDeparture: Boolean,
  gateArrival: Boolean,
  gateDeparture: Boolean,
  arrivalGateChange: Boolean,
  departureGateChange: Boolean,
  baggage: Boolean
)

case class FSCreateAlertRequest (
  extendedOptions: Option[FSRequested[String]],
  codeType: Option[FSRequested[String]],
  nameValues: Option[Seq[FSRequested[String]]],
  events: Option[Seq[FSRequested[String]]],
  deliverTo: FSRequested[String],
  airport: Option[FSRequestedAirport],
  url: URL,
  departureAirport: Option[FSRequestedAirport],
  arrivalAirport: Option[FSRequestedAirport],
  airlineCode: FSRequestedAirline,
  flightNumber: FSRequested[String],
  date: FSRequestedDate,
  name: FSRequested[String],
  description: FSRequested[String],
  `type`: FSRequested[String]
)

case class FSGetAlertRequest (
  extendedOptions: Option[FSRequested[String]],
  url: URL,
  ruleId: FSRequested[String]
)
