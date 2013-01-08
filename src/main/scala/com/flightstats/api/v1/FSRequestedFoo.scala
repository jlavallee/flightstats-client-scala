package com.flightstats.api.v1

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

@JsonCreator
case class FSRequested[T] (
  @JsonProperty("requested") requested: T,
  @JsonProperty("interpreted") interpreted: T,
  @JsonProperty("error") error: Option[T]
)

@JsonCreator
case class FSRequestedCode (
  @JsonProperty("reqestedCode") reqestedCode: String,
  @JsonProperty("fsCode") fsCode: String
)

@JsonCreator
case class FSAlertRequestedDate(
  @JsonProperty("year") year: String,
  @JsonProperty("month") month: String,
  @JsonProperty("day") day: String,
  @JsonProperty("interpreted") interpreted: String,
  @JsonProperty("error") error: Option[String]
)