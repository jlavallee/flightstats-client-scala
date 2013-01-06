package com.flightstats.api.v1.alerts

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


@JsonCreator
case class FSAlertRequestHolder (
  @JsonProperty("request") request: FSAlertRequest
)
@JsonCreator
case class FSAlertRequest (
  @JsonProperty("rule") rule: FSAlertRule
)