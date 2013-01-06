package com.flightstats.api.v1.alerts

import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.DateTime
import com.fasterxml.jackson.annotation.JsonCreator

@JsonCreator
case class FSAlertRule (
    @JsonProperty("id") id: String,
    @JsonProperty("name") name: String,
    @JsonProperty("carrierFsCode") carrierFsCode: String,
    @JsonProperty("flightNumber") flightNumber: String,
    @JsonProperty("departureAirportFsCode") departureAirportFsCode: String,
    @JsonProperty("arrivalAirportFsCode") arrivalAirportFsCode: String,
    @JsonProperty("departure") departure: DateTime,
    @JsonProperty("arrival") arrival: DateTime,
    @JsonProperty("ruleEvents") ruleEvents: Seq[FSAlertRuleEvent]
)

@JsonCreator
case class FSAlertRuleEvent (
    @JsonProperty("type") `type` : FSAlertRuleEventType
)