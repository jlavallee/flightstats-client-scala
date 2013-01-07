package com.flightstats.api.v1.alerts

import org.joda.time.DateTime
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.net.URL
import com.flightstats.api.v1.FSAirport
import com.flightstats.api.v1.FSAirline

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
case class FSAlertRequestedInterpreted (
    @JsonProperty("requested") requested: Option[String],
    @JsonProperty("interpreted") interpreted: Option[String]
)

@JsonCreator
case class FSAlertRequestedCodeFsCode (
    @JsonProperty("reqestedCode") reqestedCode: String,
    @JsonProperty("fsCode") fsCode: String
)

@JsonCreator
case class FSAlertRequest (
    @JsonProperty("extendedOptions") extendedOptions: FSAlertRequestedInterpreted,
    // ignoring codeType
    // ignoring nameValues
    @JsonProperty("events") events: Seq[FSAlertRequestedInterpreted],
    @JsonProperty("deliverTo") deliverTo: FSAlertRequestedInterpreted,
    @JsonProperty("airport") airport: FSAlertRequestedCodeFsCode,
    @JsonProperty("url") url: URL,
    @JsonProperty("airlineCode") airlineCode: FSAlertRequestedCodeFsCode,
    @JsonProperty("flightNumber") flightNumber: FSAlertRequestedInterpreted,
    @JsonProperty("date") date: FSAlertRequestDate,
    @JsonProperty("name") name: FSAlertRequestedInterpreted,
    @JsonProperty("description") description: FSAlertRequestedInterpreted,
    @JsonProperty("type") `type`: FSAlertRequestedInterpreted
)

@JsonCreator
case class FSAlertRequestDate(
    @JsonProperty("interpreted") interpreted: String,
    @JsonProperty("day") day: String,
    @JsonProperty("month") month: String,
    @JsonProperty("year") year: String
)
