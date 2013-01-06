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
  def url = requestBase // a shortcut-name for brevity
  def requestBase: RequestBuilder = apiLocation.foldLeft(flightStatsHost)(_ / _)

  protected def getWithCreds(url: RequestBuilder): Promise[Either[Throwable, String]]

  def getAndDeserialize[T](t: Class[T], url: RequestBuilder): Promise[Either[Throwable, T]] =
    for ( a <- getWithCreds(url).right ) yield mapFromJson(t, a)

  protected def addParams(url: RequestBuilder) =
      url <<? Map("appId" -> appId,
                   "appKey" -> appKey,
                   // TODO: add mechanism for users to add more extendedOptions
                   "extendedOptions" ->"useHttpErrors"
                  )
}

class RequestVerbsWithDateHandling(override val subject: RequestBuilder) extends DefaultRequestVerbs(subject) {
  def / (date: DateTime): RequestBuilder =
    subject / date.toString("yyyy") / date.toString("MM") / date.toString("dd")
}


trait FSClientReboot extends FSClient {

  def getWithCreds(url: RequestBuilder) =
      Http( addParams(url) OK as.String).either
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
