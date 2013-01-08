package com.flightstats.api.v1

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

@JsonCreator
case class FSRequestedCodeFsCode (
    @JsonProperty("reqestedCode") reqestedCode: String,
    @JsonProperty("fsCode") fsCode: String
)
