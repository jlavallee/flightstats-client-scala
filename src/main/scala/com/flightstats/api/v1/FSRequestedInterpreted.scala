package com.flightstats.api.v1

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

@JsonCreator
case class FSRequestedInterpreted (
    @JsonProperty("requested") requested: Option[String],
    @JsonProperty("interpreted") interpreted: Option[String]
)