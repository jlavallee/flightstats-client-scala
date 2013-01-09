package com.flightstats.client

import dispatch._
import com.ning.http.client.RequestBuilder
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.databind.DeserializationFeature
import org.joda.time.DateTime

protected trait FSClient {
  private val HOST = "api.flightstats.com"
  protected val appId: String
  protected val appKey: String

  private def flightStatsHost = host(HOST).secure
  protected def apiLocation: Seq[String]
  def mapFromJson[T](t: Class[T], json: String): T

  protected def url = requestBase // a shortcut-name for brevity
  protected def extendedOptions: Seq[String] = Seq.empty

  private def requestBase: RequestBuilder = apiLocation.foldLeft(flightStatsHost)(_ / _)

  protected def getWithCreds(url: RequestBuilder): Promise[String]

  protected def getAndDeserialize[T](t: Class[T], url: RequestBuilder): Promise[T] =
    for ( a <- getWithCreds(addParams(url)) ) yield mapFromJson(t, a)

  protected def addParams(url: RequestBuilder): RequestBuilder = {
    // TODO: add mechanism for users to add more extendedOptions
    url.addQueryParameter("extendedOptions",
        extendedOptions.foldLeft("useHttpErrors")( _ + "," + _))

    for((name, value) <- authParams)
      url.addHeader(name, value)
    url
  }

  private val authParams =
    Map("appId" -> appId,
        "appKey" -> appKey)
}

class RequestVerbsWithDateHandling(override val subject: RequestBuilder) extends DefaultRequestVerbs(subject) {
  def / (date: DateTime): RequestBuilder =
    subject / date.toString("yyyy") / date.toString("MM") / date.toString("dd")
}


trait FSClientReboot extends FSClient {
  def getWithCreds(url: RequestBuilder) : Promise[String] =
      Http( url OK as.String)
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
