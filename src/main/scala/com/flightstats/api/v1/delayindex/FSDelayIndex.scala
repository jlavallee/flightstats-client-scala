package com.flightstats.api.v1.delayindex

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import com.flightstats.api.v1.{FSRequestedCodeFsCode, FSRequestedInterpreted}
import java.net.URL
import org.joda.time.DateTime
import com.flightstats.api.v1.FSAirport

@JsonCreator
case class FSDelayIndexResponse (
  @JsonProperty("request") request: FSDelayIndexRequest,
  @JsonProperty("airportCodes") airportCodes: FSRequestedCodeFsCode,
  @JsonProperty("codeType") codeType: FSRequestedInterpreted,
  @JsonProperty("url") url: URL,
  @JsonProperty("classification") classification: FSRequestedInterpreted,
  @JsonProperty("score") score: FSRequestedInterpreted,
  @JsonProperty("delayIndexes") delayIndexes: Seq[FSDelayIndex]
)

@JsonCreator
case class FSDelayIndexRequest (
    @JsonProperty("airportCodes") airportCodes: Seq[FSRequestedCodeFsCode]
)

@JsonCreator
case class FSDelayIndex (
    @JsonProperty("airport") airport: FSAirport,
    @JsonProperty("rawScore") rawScore: BigDecimal, // 2.32,
    @JsonProperty("normalizedScore") normalizedScore: BigDecimal, // 0.25,
    @JsonProperty("dateStart") dateStart: DateTime, // "2013-01-08T04:00:00.000Z",
    @JsonProperty("dateEnd") dateEnd: DateTime, // "2013-01-08T07:00:00.000Z",
    @JsonProperty("flights") flights: Integer, // 19,
    @JsonProperty("observations") observations: Integer, // 16,
    @JsonProperty("canceled") canceled: Integer, // 0,
    @JsonProperty("onTime") onTime: Integer, // 14,
    @JsonProperty("delayed15") delayed15: Integer, // 0,
    @JsonProperty("delayed30") delayed30: Integer, // 2,
    @JsonProperty("delayed45") delayed45: Integer, // 0,
    @JsonProperty("delta") delta: Integer // 0,
)
