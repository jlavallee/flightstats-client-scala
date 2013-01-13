package com.flightstats.api.v1.alerts

import java.net.URL
import org.joda.time.DateTime
import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import com.flightstats.api.v1._

@JsonCreator
case class FSAlert (
  @JsonProperty("rule") rule: FSAlertRule,
  @JsonProperty("alertCapabilities") alertCapabilities: FSAlertCapabilities,
  @JsonProperty("appendix") appendix: FSAlertAppendix,
  @JsonProperty("request") request: FSAlertRequest,
  @JsonProperty("error") error: Option[FSError]
)

@JsonCreator
case class FSAlertRule (
  @JsonProperty("id") id: String,
  @JsonProperty("name") name: String,
  @JsonProperty("carrierFsCode") carrierFsCode: String,
  @JsonProperty("flightNumber") flightNumber: String,
  @JsonProperty("departure") departure: DateTime,
  @JsonProperty("departureAirport") departureAirport: Option[String],
  @JsonProperty("departureAirportFsCode") departureAirportFsCode: Option[String],
  @JsonProperty("arrival") arrival: DateTime,
  @JsonProperty("arrivalAirport") arrivalAirport: Option[String],
  @JsonProperty("arrivalAirportFsCode") arrivalAirportFsCode: Option[String],
  @JsonProperty("nameValues") nameValues: Seq[FSNameValue],
  @JsonProperty("ruleEvents") ruleEvents: Seq[FSAlertRuleEvent],
  @JsonProperty("delivery") delivery: FSAlertRuleDelivery
)

@JsonCreator
case class FSNameValue (
  @JsonProperty("name") name: String,
  @JsonProperty("value") value: String
)

@JsonCreator
case class FSAlertRuleEvent (
  @JsonProperty("type") eventType : FSAlertRuleEventType
)

@JsonCreator
case class FSAlertRuleDelivery(
  @JsonProperty("destination") destination: URL,
  @JsonProperty("format") format: String
)

@JsonCreator
case class FSAlertCapabilities(
  @JsonProperty("runwayArrival") runwayArrival: Boolean,
  @JsonProperty("runwayDeparture") runwayDeparture: Boolean,
  @JsonProperty("gateArrival") gateArrival: Boolean,
  @JsonProperty("gateDeparture") gateDeparture: Boolean,
  @JsonProperty("arrivalGateChange") arrivalGateChange: Boolean,
  @JsonProperty("departureGateChange") departureGateChange: Boolean,
  @JsonProperty("baggage") baggage: Boolean
)

@JsonCreator
case class FSAlertAppendix (
  @JsonProperty("airports") airports: Seq[FSAirport],
  @JsonProperty("airlines") airlines: Seq[FSAirline]
)

@JsonCreator
case class FSAlertRequest (
  @JsonProperty("extendedOptions") extendedOptions: FSRequested[String],
  @JsonProperty("codeType") codeType: FSRequested[String],
  @JsonProperty("nameValues") nameValues: Seq[FSRequested[String]],
  @JsonProperty("events") events: Seq[FSRequested[String]],
  @JsonProperty("deliverTo") deliverTo: FSRequested[String],
  @JsonProperty("airport") airport: FSRequestedCode,
  @JsonProperty("url") url: URL,
  @JsonProperty("ruleId") ruleId: FSRequested[String],
  @JsonProperty("departureAirport") departureAirport: FSRequestedCode,
  @JsonProperty("arrivalAirport") arrivalAirport: FSRequestedCode,
  @JsonProperty("airlineCode") airlineCode: FSRequestedCode,
  @JsonProperty("flightNumber") flightNumber: FSRequested[String],
  @JsonProperty("date") date: FSAlertRequestedDate,
  @JsonProperty("name") name: FSRequested[String],
  @JsonProperty("description") description: FSRequested[String],
  @JsonProperty("type") `type`: FSRequested[String]
)
