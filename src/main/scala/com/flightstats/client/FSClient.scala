package com.flightstats.client

import dispatch._
import com.ning.http.client.RequestBuilder
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.databind.DeserializationFeature
import org.joda.time.DateTime

protected trait FSClient {
  def api: RequestBuilder  // base URL for API
  def extendedOptions: Seq[String] = Seq.empty
  def mapFromJson[T](t: Class[T], json: String): T

  protected def appId: String
  protected def appKey: String
  protected def getAndDeserialize[T](t: Class[T], url: RequestBuilder): Promise[T]
  protected def getWithCreds(url: RequestBuilder): Promise[String]
}

protected trait FSClientBase extends FSClient with JacksonMapper {
  private val HOST = "api.flightstats.com"
  def fsHost = host(HOST).secure

  override protected def getAndDeserialize[T](t: Class[T], url: RequestBuilder): Promise[T] =
    for ( a <- getWithCreds(addParams(url)) ) yield mapFromJson(t, a)

  protected def addParams(url: RequestBuilder): RequestBuilder = {
    // TODO: add mechanism for users to add more extendedOptions
    url.addQueryParameter("extendedOptions",
        extendedOptions.foldLeft("useHttpErrors")( _ + "," + _))

    for((name, value) <- credentials)
      url.addHeader(name, value)
    url
  }

  private val credentials = Map("appId" -> appId, "appKey" -> appKey)
}

/**
 * adds support for adding /YYYY/MM/DD to a RequestBuilder URL using the / operator
 */
protected class RequestVerbsWithDateHandling(override val subject: RequestBuilder) extends DefaultRequestVerbs(subject) {
  def / (date: DateTime): RequestBuilder =
    subject / date.toString("yyyy") / date.toString("MM") / date.toString("dd")
}


trait FSClientReboot extends FSClient {
  override protected def getWithCreds(url: RequestBuilder): Promise[String] =
      Http( url OK as.String)
}

trait JacksonMapper extends FSClient {
  override def mapFromJson[T](t: Class[T], json: String): T =
    JacksonMapper.mapper.readValue(json, t)
}

private object JacksonMapper {
  def mapper = {
    val m = new ObjectMapper()
    m.registerModule(DefaultScalaModule)
    m.registerModule(new JodaModule())
    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    m
  }
}