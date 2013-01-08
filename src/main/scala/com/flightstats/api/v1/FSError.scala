package com.flightstats.api.v1

import java.util.UUID
import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

@JsonCreator
case class FSError(
  @JsonProperty("httpStatusCode") httpStatusCode: Integer,
  @JsonProperty("errorCode") errorCode: String, // TODO: make an enum for these
  @JsonProperty("errorId") errorId: UUID,
  @JsonProperty("errorMessage") errorMessage: String
)
