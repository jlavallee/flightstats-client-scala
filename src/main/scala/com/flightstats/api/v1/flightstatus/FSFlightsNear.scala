package com.flightstats.api.v1.flightstatus

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import java.math.BigDecimal
import com.flightstats.api.v1.{FSAppendix, FSRequested, FSPosition}
import org.joda.time.DateTime
import java.net.URL

@JsonCreator
case class FSFlightsNearBoundingBox (
  @JsonProperty("request") request: FSFlightsNearBoundingBoxRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("flightPositions") flightPositions: Seq[FSFlightPosition]
)

@JsonCreator
case class FSFlightsNearBoundingBoxRequest (
  @JsonProperty("topLatitude") topLatitude: FSRequested[BigDecimal],
  @JsonProperty("leftLongitude") leftLongitude: FSRequested[BigDecimal],
  @JsonProperty("bottomLatitude") bottomLatitude: FSRequested[BigDecimal],
  @JsonProperty("rightLongitude") rightLongitude: FSRequested[BigDecimal],
  @JsonProperty("extendedOptions") extendedOptions: FSRequested[String],
  @JsonProperty("url") url: Option[URL]
)

@JsonCreator
case class FSFlightsNearPointAndDistance (
  @JsonProperty("request") request: FSFlightsNearPointAndDistanceRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("flightPositions") flightPositions: Seq[FSFlightPosition]
)

@JsonCreator
case class FSFlightsNearPointAndDistanceRequest (
  @JsonProperty("latitude") latitude: FSRequested[BigDecimal],
  @JsonProperty("longitude") longitude: FSRequested[BigDecimal],
  @JsonProperty("radiusMiles") radiusMiles: FSRequested[Integer],
  @JsonProperty("maxFlights") maxFlights: FSRequested[Integer],
  @JsonProperty("extendedOptions") extendedOptions: FSRequested[String],
  @JsonProperty("url") url: Option[URL]
)

@JsonCreator
case class FSFlightPosition (
  @JsonProperty("flightId") flightId: Long,
  @JsonProperty("callsign") callsign: String,
  @JsonProperty("heading") heading: BigDecimal,
  @JsonProperty("source") source: String,
  @JsonProperty("positions") positions: Seq[FSPosition]
)