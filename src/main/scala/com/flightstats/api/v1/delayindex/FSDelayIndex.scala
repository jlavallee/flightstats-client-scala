package com.flightstats.api.v1.delayindex

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import java.net.URL
import org.joda.time.DateTime
import java.math.BigDecimal  // Java's BigDecimal string & integer constructors
import com.flightstats.api.v1.{FSAirport, FSError, FSRequested, FSRequestedAirport}

case class FSDelayIndexResponse (
  request: FSDelayIndexRequest,
  airportCodes: FSRequestedAirport,
  codeType: FSRequested[String],
  url: URL,
  classification: FSRequested[Integer],
  score: FSRequested[BigDecimal],
  delayIndexes: Seq[FSDelayIndex],
  error: Option[FSError]
)

case class FSDelayIndexRequest (
  airportCodes: Seq[FSRequestedAirport],
  codeType: FSRequested[String],
  country: FSRequested[String],
  region: FSRequested[String],
  state: FSRequested[String],
  score: FSRequested[String],
  classification: FSRequested[Integer],
  url: Option[URL]
)

case class FSDelayIndex (
  airport: FSAirport,
  rawScore: BigDecimal, // 2.32,
  normalizedScore: BigDecimal, // 0.25,
  dateStart: DateTime, // "2013-01-08T04:00:00.000Z",
  dateEnd: DateTime, // "2013-01-08T07:00:00.000Z",
  flights: Integer, // 19,
  observations: Integer, // 16,
  canceled: Integer, // 0,
  onTime: Integer, // 14,
  delayed15: Integer, // 0,
  delayed30: Integer, // 2,
  delayed45: Integer, // 0,
  delta: Integer // 0,
)
