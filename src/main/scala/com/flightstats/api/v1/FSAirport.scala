package com.flightstats.api.v1

import org.joda.time.DateTime
import java.math.BigDecimal  // Java's BigDecimal string & integer constructors
import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

@JsonCreator
case class FSAirportsHolder(
  @JsonProperty("airports") airports: Seq[FSAirport]
)

@JsonCreator
case class FSAirportHolder(
  @JsonProperty("airport") airport: FSAirport
)

@JsonCreator
class FSAirport (  // too many parameters for a case class...
  @JsonProperty("fs") val fs: String,
  @JsonProperty("iata") val iata: Option[String],
  @JsonProperty("icao") val icao: Option[String],
  @JsonProperty("faa") val faa: Option[String],
  @JsonProperty("name") val name: Option[String],
  @JsonProperty("street1") val street1: Option[String],
  @JsonProperty("street2") val street2: Option[String],
  @JsonProperty("city") val city: String,
  @JsonProperty("cityCode") val cityCode: Option[String],
  @JsonProperty("district") val district: Option[String],
  @JsonProperty("stateCode") val stateCode: Option[String],
  @JsonProperty("postalCode") val postalCode: Option[String],
  @JsonProperty("countryCode") val countryCode: String,
  @JsonProperty("countryName") val countryName: String,
  @JsonProperty("regionName") val regionName: String,
  @JsonProperty("timeZoneRegionName") val timeZoneRegionName: String,
  @JsonProperty("weatherZone") val weatherZone: Option[String],
  @JsonProperty("localTime") val localTime: String,
  @JsonProperty("utcOffsetHours") val utcOffsetHours: BigDecimal,
  @JsonProperty("latitude") val latitude: BigDecimal,
  @JsonProperty("longitude") val longitude: BigDecimal,
  @JsonProperty("elevationFeet") val elevationFeet: Option[Integer],
  @JsonProperty("classification") val classification: Integer,
  @JsonProperty("active") val active: Boolean,
  @JsonProperty("dateFrom") val dateFrom: Option[String],
  @JsonProperty("dateTo") val dateTo: Option[String],
  @JsonProperty("delayIndexUrl") val delayIndexUrl: Option[String],
  @JsonProperty("weatherUrl") val weatherUrl: Option[String]
)

