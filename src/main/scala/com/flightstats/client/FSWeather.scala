package com.flightstats.client

import dispatch._
import org.joda.time.DateTime
import com.ning.http.client.RequestBuilder
import com.flightstats.client._
import com.flightstats.api.v1.weather.{FSWeatherMetar, FSWeatherTaf, FSWeatherZoneForecast, FSWeatherAll}

/** Factory for [[com.flightstats.client.FSWeather]] instances. */
object FSWeather {
  def apply(appId: String, appKey: String): FSWeather = {
    new FSWeather(appId, appKey) with FSClientReboot
  }
}
/** A client for FlightStats Weather API
  *
  * Use companion object to obtain an instance:
  *
  * {{{
  *   val weatherClient = FSWeather("my app id", "my app key")
  * }}}
  *
  * @see <a target="_top" href="https://developer.flightstats.com/api-docs/weather/v1">FlightStats Weather API Documentation</a>
  */
abstract class FSWeather(protected val appId: String, protected val appKey: String) extends FSClientBase {
  // https://api.flightstats.com/flex/weather/rest/v1/json
  protected def api = fsHost / "flex" / "weather" / "rest" / "v1" / "json"


  /** All weather products for airport
    *
    * /v1/json/all/{airport} GET
    */
  def all(airportCode: String, args: ArgMap = Map.empty) =
    getAndDeserialize(classOf[FSWeatherAll],
        api / "all" / airportCode <<? args
    )

  /** METAR for airport
    *
    * /v1/json/metar/{airport} GET
    */
  def metar(airportCode: String, args: ArgMap = Map.empty) =
    getAndDeserialize(classOf[FSWeatherMetar],
        api / "metar" / airportCode <<? args
    )

  /** TAF for airport
    *
    * /v1/json/taf/{airport} GET
    */
  def taf(airportCode: String, args: ArgMap = Map.empty) =
    getAndDeserialize(classOf[FSWeatherTaf],
        api / "taf" / airportCode <<? args
    )

  /** Zone Forecast for airport
    *
    * /v1/json/zf/{airport} GET
    */
  def zoneForecast(airportCode: String, args: ArgMap = Map.empty) =
    getAndDeserialize(classOf[FSWeatherZoneForecast],
        api / "zf" / airportCode <<? args
    )

}
