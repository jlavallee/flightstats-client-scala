package com.flightstats.api.v1.delayindex

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import java.net.URL
import org.joda.time.DateTime
import java.math.BigDecimal  // Java's BigDecimal string & integer constructors
import com.flightstats.api.v1.{FSAirport, FSError, FSRequestedCode, FSRequested}

@JsonCreator
case class FSDelayIndexResponse (
  @JsonProperty("request") request: FSDelayIndexRequest,
  @JsonProperty("airportCodes") airportCodes: FSRequestedCode,
  @JsonProperty("url") url: URL,
  @JsonProperty("classification") classification: FSRequested[Integer],
  @JsonProperty("score") score: FSRequested[BigDecimal],
  @JsonProperty("delayIndexes") delayIndexes: Seq[FSDelayIndex],
  @JsonProperty("error") error: Option[FSError]
)

@JsonCreator
case class FSDelayIndexRequest (
  @JsonProperty("airportCodes") airportCodes: Seq[FSRequestedCode],
  @JsonProperty("codeType") codeType: FSRequested[String],
  @JsonProperty("country") country: FSRequested[String],
  @JsonProperty("region") region: FSRequested[String],
  @JsonProperty("state") state: FSRequested[String],
  @JsonProperty("score") score: FSRequested[String],
  @JsonProperty("classification") classification: FSRequested[Integer],
  @JsonProperty("url") url: Option[URL]
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
