package com.flightstats.api.v1

import org.joda.time.DateTime
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
case class FSAirport(
   @JsonProperty("fs") fs: String, // "CLO",
   @JsonProperty("iata") iata: String, // "CLO",
   @JsonProperty("icao") icao: String, // "SKCL",
   @JsonProperty("name") name: String, // "Alfonso B. Aragon Airport",
   @JsonProperty("city") city: String, // "Cali",
   @JsonProperty("cityCode") cityCode: String, // "CLO",
   @JsonProperty("countryCode") countryCode: String, // "CO",
   @JsonProperty("countryName") countryName: String, // "Colombia",
   @JsonProperty("regionName") regionName: String, // "South America",
   @JsonProperty("timeZoneRegionName") timeZoneRegionName: String, // "America/Bogota",
   @JsonProperty("localTime") localTime: DateTime, // "2013-01-05T16:06:56.183",
   @JsonProperty("utcOffsetHours") utcOffsetHours: Integer, // -5,
   @JsonProperty("latitude") latitude: BigDecimal, // 3.543056,
   @JsonProperty("longitude") longitude: BigDecimal, // -76.381389,
   @JsonProperty("elevationFeet") elevationFeet: Integer, // 3162,
   @JsonProperty("classification") classification: String, // 3,
   @JsonProperty("active") active: Boolean, // true,
   @JsonProperty("delayIndexUrl") delayIndexUrl: String, // "https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/CLO?codeType=fs",
   @JsonProperty("weatherUrl") weatherUrl: String // "https://api.flightstats.com/flex/weather/rest/v1/json/all/CLO?codeType=fs"
)
