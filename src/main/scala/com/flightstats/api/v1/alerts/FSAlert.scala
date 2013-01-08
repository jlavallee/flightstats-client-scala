package com.flightstats.api.v1.alerts

import org.joda.time.DateTime
import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import java.net.URL
import com.flightstats.api.v1.{FSAirport, FSAirline, FSRequestedInterpreted, FSRequestedCodeFsCode}

@JsonCreator
case class FSAlert (
  @JsonProperty("rule") rule: FSAlertRule,
  @JsonProperty("alertCapabilities") alertCapabilities: FSAlertCapabilities,
  @JsonProperty("appendix") appendix: FSAlertAppendix,
  @JsonProperty("request") request: FSAlertRequest
)

@JsonCreator
case class FSAlertRule (
    @JsonProperty("id") id: String,
    @JsonProperty("name") name: String,
    @JsonProperty("carrierFsCode") carrierFsCode: String,
    @JsonProperty("flightNumber") flightNumber: String,
    @JsonProperty("departureAirportFsCode") departureAirportFsCode: Option[String],
    @JsonProperty("arrivalAirportFsCode") arrivalAirportFsCode: Option[String],
    @JsonProperty("departure") departure: DateTime,
    @JsonProperty("arrival") arrival: DateTime,
    @JsonProperty("ruleEvents") ruleEvents: Seq[FSAlertRuleEvent],
    @JsonProperty("delivery") delivery: FSAlertRuleDelivery
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
    @JsonProperty("extendedOptions") extendedOptions: FSRequestedInterpreted,
    // ignoring codeType
    // ignoring nameValues
    @JsonProperty("events") events: Seq[FSRequestedInterpreted],
    @JsonProperty("deliverTo") deliverTo: FSRequestedInterpreted,
    @JsonProperty("airport") airport: FSRequestedCodeFsCode,
    @JsonProperty("url") url: URL,
    @JsonProperty("airlineCode") airlineCode: FSRequestedCodeFsCode,
    @JsonProperty("flightNumber") flightNumber: FSRequestedInterpreted,
    @JsonProperty("date") date: FSAlertRequestDate,
    @JsonProperty("name") name: FSRequestedInterpreted,
    @JsonProperty("description") description: FSRequestedInterpreted,
    @JsonProperty("type") `type`: FSRequestedInterpreted
)

@JsonCreator
case class FSAlertRequestDate(
    @JsonProperty("interpreted") interpreted: String,
    @JsonProperty("day") day: String,
    @JsonProperty("month") month: String,
    @JsonProperty("year") year: String
)
