package com.flightstats.api.v1.ratings

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import java.net.URL
import com.flightstats.api.v1.{FSAppendix, FSRequested, FSAirport, FSRequestedAirline}

@JsonCreator
case class FSRatingsForRoute (
  @JsonProperty("request") request: FSRatingsForRouteRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("ratings") ratings: Seq[FSRating]
)

@JsonCreator
case class FSRatingsForRouteRequest (
  @JsonProperty("departureAirport") departureAirport: FSRequested[String],
  @JsonProperty("arrivalAirport") arrivalAirport: FSRequested[String],
  @JsonProperty("codeType") codeType: FSRequested[String],
  @JsonProperty("extendedOptions") extendedOptions: FSRequested[String],
  @JsonProperty("url") url: URL
)

@JsonCreator
case class FSRatingsForFlight (
  @JsonProperty("request") request: FSRatingsForFlightRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("ratings") ratings: Seq[FSRating]
)

@JsonCreator
case class FSRatingsForFlightRequest (
  @JsonProperty("airlineCode") airlineCode: FSRequestedAirline,
  @JsonProperty("flightNumber") flightNumber: FSRequested[String],
  @JsonProperty("departureAirport") departureAirport: FSRequested[String],
  @JsonProperty("codeType") codeType: FSRequested[String],
  @JsonProperty("extendedOptions") extendedOptions: FSRequested[String],
  @JsonProperty("url") url: URL
)

@JsonCreator
class FSRating ( // past case class limit of 22 parameters
  @JsonProperty("departureAirport") val departureAirport: Option[FSAirport],
  @JsonProperty("departureAirportFsCode") val departureAirportFsCode: Option[String],
  @JsonProperty("arrivalAirport") val arrivalAirport: Option[FSAirport],
  @JsonProperty("arrivalAirportFsCode") val arrivalAirportFsCode: Option[String],
  @JsonProperty("airlineFsCode") val airlineFsCode: Option[String],
  @JsonProperty("flightNumber") val flightNumber: Option[String],
  @JsonProperty("codeshares") val codeshares: Integer,
  @JsonProperty("directs") val directs: Integer,
  @JsonProperty("observations") val observations: Integer,
  @JsonProperty("ontime") val ontime: Integer,
  @JsonProperty("late15") val late15: Integer,
  @JsonProperty("late30") val late30: Integer,
  @JsonProperty("late45") val late45: Integer,
  @JsonProperty("cancelled") val cancelled: Integer,
  @JsonProperty("diverted") val diverted: Integer,
  @JsonProperty("ontimePercent") val ontimePercent: BigDecimal,
  @JsonProperty("delayObservations") val delayObservations: Integer,
  @JsonProperty("delayMean") val delayMean: BigDecimal,
  @JsonProperty("delayStandardDeviation") val delayStandardDeviation: BigDecimal,
  @JsonProperty("delayMin") val delayMin: Integer,
  @JsonProperty("delayMax") val delayMax: Integer,
  @JsonProperty("allOntimeCumulative") val allOntimeCumulative: BigDecimal,
  @JsonProperty("allOntimeStars") val allOntimeStars: BigDecimal,
  @JsonProperty("allDelayCumulative") val allDelayCumulative: BigDecimal,
  @JsonProperty("allDelayStars") val allDelayStars: BigDecimal,
  @JsonProperty("allStars") val allStars: BigDecimal
)
