package com.flightstats.api.v1

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

@JsonCreator
case class FSAirline(
    @JsonProperty("active") active: String, // true
    @JsonProperty("phoneNumber") phoneNumber: String, // "1-800-433-7300"
    @JsonProperty("name") name: String, // "American Airlines"
    @JsonProperty("icao") icao: String, // "AAL"
    @JsonProperty("iata") iata: String, // "AA"
    @JsonProperty("fs") fs: String  // "AA"
)
