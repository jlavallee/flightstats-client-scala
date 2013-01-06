package com.flightstats.client

import dispatch._
import com.ning.http.client.RequestBuilder
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.databind.DeserializationFeature
import org.joda.time.DateTime

protected trait FSClient {
  val HOST = "api.flightstats.com"
  def flightStatsHost = host(HOST).secure
  def apiLocation: Seq[String]
  def mapFromJson[T](t: Class[T], json: String): T

  val appId: String
  val appKey: String
  def requestBase: RequestBuilder = apiLocation.foldLeft(flightStatsHost)(_ / _)
  def datePieces(date: DateTime): List[String] =
    List(date.toString("yyyy"), date.toString("MM"), date.toString("dd"))

  def getAndDeserialize[T](t: Class[T], pathParts: String*): Promise[Either[Throwable, T]] =
    for ( a <- getWithCreds(pathParts:_*).right ) yield mapFromJson(t, a)

  protected def getWithCreds(pathParts: String*): Promise[Either[Throwable, String]]
}

trait FSClientReboot extends FSClient {
  def getWithCreds(pathParts: String*): Promise[Either[Throwable, String]] = {
    getWithCreds(pathParts.foldLeft(requestBase)(_ / _))
  }

  def getWithCreds(url: RequestBuilder) =
      Http(url <<? Map("appId" -> appId,
                       "appKey" -> appKey,
                       // TODO: add mechanism for users to add more extendedOptions
                       "extendedOptions" ->"useHttpErrors"
                      )
          OK as.String).either
}

private object JacksonMapper {
  def mapper = initMapper
  private def initMapper = {
    val m = new ObjectMapper()
    m.registerModule(DefaultScalaModule)
    m.registerModule(new JodaModule())
    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    m
  }
}

trait JacksonMapper {
  def mapFromJson[T](t: Class[T], json: String): T =
    JacksonMapper.mapper.readValue(json, t)
}
