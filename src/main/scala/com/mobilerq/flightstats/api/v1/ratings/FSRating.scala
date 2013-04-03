package com.mobilerq.flightstats.api.v1.ratings

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import java.net.URL
import com.mobilerq.flightstats.api.v1._

case class FSRatingsForRoute (
  request: FSRatingsForRouteRequest,
  appendix: FSAppendix,
  ratings: Seq[FSRating]
)

case class FSRatingsForRouteRequest (
  departureAirport: FSRequested[String],
  arrivalAirport: FSRequested[String],
  codeType: FSRequested[String],
  extendedOptions: FSRequested[String],
  url: URL
)

case class FSRatingsForFlight (
  request: FSRatingsForFlightRequest,
  appendix: FSAppendix,
  ratings: Seq[FSRating]
)

case class FSRatingsForFlightRequest (
  airlineCode: FSRequestedAirline,
  flightNumber: FSRequested[String],
  departureAirport: FSRequested[String],
  codeType: FSRequested[String],
  extendedOptions: FSRequested[String],
  url: URL
)

@JsonCreator
class FSRating ( // past case class limit of 22 parameters
  @JsonProperty("departureAirportFsCode") val departureAirportFsCode: Option[String],
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

class RichFSRatingsForRoute(response: FSRatingsForRoute)
  extends FSRatingsForRoute(response.request, response.appendix, response.ratings) {
  override val ratings = response.ratings map { new RichFSRating(_, response.appendix)}
}

class RichFSRatingsForFlight(response: FSRatingsForFlight)
  extends FSRatingsForFlight(response.request, response.appendix, response.ratings) {
  override val ratings = response.ratings map { new RichFSRating(_, response.appendix)}
}

class RichFSRating(r: FSRating, val appendix: FSAppendix)
  extends FSRating(r.departureAirportFsCode,
      r.arrivalAirportFsCode,
      r.airlineFsCode,
      r.flightNumber, r.codeshares,
      r.directs, r.observations, r.ontime,
      r.late15, r.late30, r.late45,
      r.cancelled, r.directs, r.ontimePercent,
      r.delayObservations, r.delayMean, r.delayStandardDeviation,
      r.delayMin, r.delayMax,
      r.allOntimeCumulative, r.allOntimeStars,
      r.allDelayCumulative, r.allDelayCumulative,
      r.allStars)
  with FlightAppendixHelper
  with AirlineAppendixHelper