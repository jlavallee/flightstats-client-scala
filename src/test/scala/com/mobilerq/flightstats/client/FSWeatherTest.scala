package com.mobilerq.flightstats.client

import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Success, Failure}
import org.junit.Test
import org.junit.Assert._
import org.joda.time.DateTime
import com.mobilerq.flightstats.api.v1.weather.{FSWeatherMetar, FSWeatherTaf, FSWeatherZoneForecast, FSMetar, FSTaf, FSZoneForecast, FSWeatherAll}

class FSWeatherTest extends FSTest {
  val weather = FSTestClients.weather

  @Test def factory: Unit = FSWeather("id", "key") match {
    case o: FSWeather => Unit // what we expect
    case x => fail("didn't get what we expected: " + x)
  }

  @Test def all =
    checkWeatherResponse(weather.all("PDX"))

  @Test def metar =
    checkWeatherResponse(weather.metar("PDX"))

  @Test def taf =
    checkWeatherResponse(weather.taf("PDX"))

  @Test def zoneForecast =
    checkWeatherResponse(weather.zoneForecast("PDX"))


  def checkWeatherResponse(weatherResponse: Future[AnyRef]) {
    debug(weatherResponse)
    val response = Await.result(weatherResponse, duration);
    exerciseCaseClass(response)
    response match {
      case FSWeatherMetar(req, appendix, metar) => checkMetar(metar)
      case FSWeatherTaf(req, appendix, taf) => checkTaf(taf)
      case FSWeatherZoneForecast(req, appendix, weather) => checkZoneForecast(weather)
      case FSWeatherAll(req, appendix, metar, taf, zoneForcast) => {
        checkMetar(metar)
        checkTaf(taf)
        checkZoneForecast(zoneForcast)
        }
    }
  }

  def checkMetar(weather: FSMetar) {
    assertNotNull(weather)
  }
  def checkTaf(weather: FSTaf) {
    assertNotNull(weather)
  }
  def checkZoneForecast(forecast: FSZoneForecast) {
    assertNotNull(forecast)
    assertTrue(forecast.cities.length > 0)
  }
}