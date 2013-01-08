package com.flightstats.api.v1

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

@JsonCreator
case class FSRequestedInterpreted (
    @JsonProperty("requested") requested: String,
    @JsonProperty("interpreted") interpreted: String
)

@JsonCreator
case class FSRequestedCodeFsCode (
    @JsonProperty("reqestedCode") reqestedCode: String,
    @JsonProperty("fsCode") fsCode: String
)

@JsonCreator
case class FSRequestedError (
    @JsonProperty("requested") requested: String,
    @JsonProperty("error") interpreted: Option[String]
)