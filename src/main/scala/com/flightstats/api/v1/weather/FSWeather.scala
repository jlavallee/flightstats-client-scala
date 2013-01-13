package com.flightstats.api.v1.weather

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import java.net.URL
import com.flightstats.api.v1.{FSAppendix, FSRequested}
import org.joda.time.DateTime

@JsonCreator
case class FSWeatherAll (
  @JsonProperty("request") request: FSWeatherRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("metar") metar: FSMetar,
  @JsonProperty("taf") taf: FSTaf,
  @JsonProperty("zoneForecast") zoneForecast: FSZoneForecast
)

@JsonCreator
case class FSWeatherMetar (
  @JsonProperty("request") request: FSWeatherRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("metar") metar: FSMetar
)

@JsonCreator
case class FSWeatherTaf (
  @JsonProperty("request") request: FSWeatherRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("taf") taf: FSTaf
)

@JsonCreator
case class FSWeatherZoneForecast (
  @JsonProperty("request") request: FSWeatherRequest,
  @JsonProperty("appendix") appendix: FSAppendix,
  @JsonProperty("zoneForecast") zoneForecast: FSZoneForecast
)

@JsonCreator
case class FSWeatherRequest(
  @JsonProperty("airport") airport: FSRequested[String],
  @JsonProperty("codeType") codeType: FSRequested[String],
  @JsonProperty("extendedOptions") extendedOptions: FSRequested[String],
  @JsonProperty("url") url: URL
)

@JsonCreator
case class FSMetar (
  @JsonProperty("report") report: String,
  @JsonProperty("reportTime") reportTime: String,
  @JsonProperty("reportModifier") reportModifier: Option[String],
  @JsonProperty("weatherStationIcao") weatherStationIcao: String,
  @JsonProperty("tags") tags: Seq[FSTag],
  @JsonProperty("conditions") conditions: FSConditions,
  @JsonProperty("temperatureCelsius") temperatureCelsius: Option[String],
  @JsonProperty("dewPointCelsius") dewPointCelsius: Option[String],
  @JsonProperty("runwayVisualRanges") runwayVisualRanges: Seq[FSRunwayVisualRange],
  @JsonProperty("obscurations") obscurations: Seq[FSWeatherObscuration],
  @JsonProperty("noSignificantChange") noSignificantChange: Boolean
)

@JsonCreator
case class FSTag (
  @JsonProperty("key") key: String,
  @JsonProperty("value") value: String
)

@JsonCreator
case class FSRunwayVisualRange (
  @JsonProperty("highestVisibility") highestVisibility: Option[Integer],
  @JsonProperty("lowestVisibility") lowestVisibility: Option[Integer],
  @JsonProperty("modifier") modifier: Option[String],
  @JsonProperty("runwayNumber") runwayNumber: Option[String]
)

@JsonCreator
case class FSWeatherObscuration (
  @JsonProperty("phenomenon") phenomenon: Option[String],
  @JsonProperty("coverage") coverage: Option[String],
  @JsonProperty("height") height: Option[Integer]
)

@JsonCreator
case class FSConditions (
  @JsonProperty("wind") wind: FSWind,
  @JsonProperty("visibility") visibility: FSVisibility,
  @JsonProperty("weatherConditions") weatherConditions: Seq[FSWeatherCondition],
  @JsonProperty("skyConditions") skyConditions: Seq[FSSkyCondition],
  @JsonProperty("pressureInchesHg") pressureInchesHg: Option[String]
)

@JsonCreator
case class FSWeatherCondition (
  @JsonProperty("phenomenon") phenomenon: Option[String],
  @JsonProperty("descriptor") descriptor: Option[String],
  @JsonProperty("intensity") intensity: Option[String]
)

@JsonCreator
case class FSWind (
  @JsonProperty("direction") direction: Option[Integer],
  @JsonProperty("directionMin") directionMin: Option[Integer],
  @JsonProperty("directionMax") directionMax: Option[Integer],
  @JsonProperty("directionIsVariable") directionIsVariable: Option[Boolean],
  @JsonProperty("speedKnots") speedKnots: Option[String],
  @JsonProperty("gustSpeedKnots") gustSpeedKnots: Option[String]
)

@JsonCreator
case class FSVisibility (
  @JsonProperty("miles") miles: Option[String],
  @JsonProperty("lessThan") lessThan: Option[Boolean],
  @JsonProperty("cavok") cavok: Option[Boolean]
)

@JsonCreator
case class FSSkyCondition (
  @JsonProperty("coverage") coverage: Option[String],
  @JsonProperty("base") base: Option[Integer],
  @JsonProperty("modifier") modifier: Option[String]
)

@JsonCreator
case class FSTaf (
  @JsonProperty("report") report: Option[String],
  @JsonProperty("reportTime") reportTime: Option[String],
  @JsonProperty("observationTime") observationTime: Option[String],
  @JsonProperty("reportType") reportType: Option[String],
  @JsonProperty("weatherStationIcao") weatherStationIcao: Option[String],
  @JsonProperty("forecasts") forecasts: Seq[FSWeather]
)

case class FSWeather (
  @JsonProperty("type") `type`: Option[String],
  @JsonProperty("start") start: Option[String],
  @JsonProperty("end") end: Option[String],
  @JsonProperty("conditions") conditions: FSConditions,
  @JsonProperty("probability") probability: Option[String]
)

@JsonCreator
case class FSZoneForecast (
  @JsonProperty("header") header: Option[String],
  @JsonProperty("general") general: Option[String],
  @JsonProperty("zones") zones: Seq[String],
  @JsonProperty("reportTime") reportTime: Option[DateTime],
  @JsonProperty("cities") cities: Seq[String],
  @JsonProperty("zoneNames") zoneNames: Seq[String],
  @JsonProperty("dayForecasts") dayForecasts: Seq[FSDayForecast]
)

case class FSDayForecast (
  @JsonProperty("day") day: Option[String],
  @JsonProperty("forecast") forecast: Option[String],
  @JsonProperty("start") start: Option[String],
  @JsonProperty("end") end: Option[String],
  @JsonProperty("tags") tags: Seq[FSTag]
)
