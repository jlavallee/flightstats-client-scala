package com.mobilerq.flightstats.client

import dispatch._
import com.ning.http.client.RequestBuilder
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.databind.DeserializationFeature
import org.joda.time.DateTime

protected trait FSClient {
  protected def api: RequestBuilder  // base URL for API
  protected def appId: String
  protected def appKey: String
  protected def getAndDeserialize[T](t: Class[T], url: RequestBuilder): Promise[T]
  protected def getWithCreds(url: RequestBuilder): Promise[String]
  protected def mapFromJson[T](t: Class[T], json: String): T
}

protected trait FSClientBase extends FSClient with JacksonMapper {
  private val HOST = "api.flightstats.com"
  protected def fsHost = host(HOST).secure

  override protected def getAndDeserialize[T](t: Class[T], url: RequestBuilder): Promise[T] =
    for ( a <- getWithCreds(addParams(url)) ) yield mapFromJson(t, a)

  protected def addParams(url: RequestBuilder): RequestBuilder = {
    url.addQueryParameter("extendedOptions", "useHttpErrors")
    credentials.foreach {case (name, value) => url.addHeader(name, value)}
    url
  }

  private val credentials = Map("appId" -> appId, "appKey" -> appKey)
}

/** adds support for DateTime & BigDecimal handling using / to RequestBuilder */
protected class EnhancedRequestVerbs(override val subject: RequestBuilder) extends DefaultRequestVerbs(subject) {
  def / (date: DateTime): RequestBuilder =
    subject / date.toString("yyyy") / date.toString("MM") / date.toString("dd")
  def / (decimal: BigDecimal): RequestBuilder =
    subject / decimal.toString
}

/** implements HTTP support using Dispatch Reboot */
trait FSClientReboot extends FSClient {
  override protected def getWithCreds(url: RequestBuilder): Promise[String] =
      Http( url OK as.String)
}

/** implements JSON mapping using Jackson, ignoring unknown properties */
trait JacksonMapper extends FSClient {
  override protected def mapFromJson[T](t: Class[T], json: String): T =
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
