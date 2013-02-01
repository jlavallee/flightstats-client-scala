package com.mobilerq.flightstats.api.v1.weather

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import java.net.URL
import com.mobilerq.flightstats.api.v1.{FSAppendix, FSRequested}
import org.joda.time.DateTime

case class FSWeatherAll (
  request: FSWeatherRequest,
  appendix: FSAppendix,
  metar: FSMetar,
  taf: FSTaf,
  zoneForecast: FSZoneForecast
)

case class FSWeatherMetar (
  request: FSWeatherRequest,
  appendix: FSAppendix,
  metar: FSMetar
)

case class FSWeatherTaf (
  request: FSWeatherRequest,
  appendix: FSAppendix,
  taf: FSTaf
)

case class FSWeatherZoneForecast (
  request: FSWeatherRequest,
  appendix: FSAppendix,
  zoneForecast: FSZoneForecast
)

case class FSWeatherRequest(
  airport: FSRequested[String],
  codeType: Option[FSRequested[String]],
  extendedOptions: Option[FSRequested[String]],
  url: URL
)

case class FSMetar (
  report: String,
  reportTime: String,
  reportModifier: Option[String],
  weatherStationIcao: String,
  tags: Seq[FSTag],
  conditions: FSConditions,
  temperatureCelsius: Option[String],
  dewPointCelsius: Option[String],
  runwayVisualRanges: Seq[FSRunwayVisualRange],
  obscurations: Seq[FSWeatherObscuration],
  noSignificantChange: Boolean
)

case class FSTag (
  key: String,
  value: String
)

case class FSRunwayVisualRange (
  highestVisibility: Option[Integer],
  lowestVisibility: Option[Integer],
  modifier: Option[String],
  runwayNumber: Option[String]
)

case class FSWeatherObscuration (
  phenomenon: Option[String],
  coverage: Option[String],
  height: Option[Integer]
)

case class FSConditions (
  wind: FSWind,
  visibility: FSVisibility,
  weatherConditions: Seq[FSWeatherCondition],
  skyConditions: Seq[FSSkyCondition],
  pressureInchesHg: Option[String]
)

case class FSWeatherCondition (
  phenomenon: Option[String],
  descriptor: Option[String],
  intensity: Option[String]
)

case class FSWind (
  direction: Option[Integer],
  directionMin: Option[Integer],
  directionMax: Option[Integer],
  directionIsVariable: Option[Boolean],
  speedKnots: Option[String],
  gustSpeedKnots: Option[String]
)

case class FSVisibility (
  miles: Option[String],
  lessThan: Option[Boolean],
  cavok: Option[Boolean]
)

case class FSSkyCondition (
  coverage: Option[String],
  base: Option[Integer],
  modifier: Option[String]
)

case class FSTaf (
  report: Option[String],
  reportTime: Option[String],
  observationTime: Option[String],
  reportType: Option[String],
  weatherStationIcao: Option[String],
  forecasts: Seq[FSWeather]
)

case class FSWeather (
  `type`: Option[String],
  start: Option[String],
  end: Option[String],
  conditions: FSConditions,
  probability: Option[String]
)

case class FSZoneForecast (
  header: Option[String],
  general: Option[String],
  zones: Seq[String],
  reportTime: Option[DateTime],
  cities: Seq[String],
  zoneNames: Seq[String],
  dayForecasts: Seq[FSDayForecast]
)

case class FSDayForecast (
  day: Option[String],
  forecast: Option[String],
  start: Option[String],
  end: Option[String],
  tags: Seq[FSTag]
)
