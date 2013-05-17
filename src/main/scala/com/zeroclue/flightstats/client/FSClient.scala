package com.zeroclue.flightstats.client

import scala.concurrent.Future
import dispatch._
import dispatch.Defaults.executor
import com.ning.http.client.{Request, RequestBuilder}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.databind.{ObjectMapper, DeserializationFeature}
import org.joda.time.DateTime
import com.google.common.cache.{CacheLoader, LoadingCache}

protected trait FSClient {
  protected def api: RequestBuilder  // base URL for API
  protected def appId: String
  protected def appKey: String
}

protected trait JsonHttpClient {
  protected def getAndDeserialize[T](t: Class[T], url: RequestBuilder): Future[T]
  protected def getWithCreds(url: RequestBuilder): Future[String]
  protected def mapFromJson[T](t: Class[T], json: String): T
}

protected trait FSClientBase extends FSClient with JsonHttpClient {
  self: FSClient with JsonHttpClient =>
  private val HOST = "api.flightstats.com"
  protected def fsHost = host(HOST).secure

  override protected def getAndDeserialize[T](t: Class[T], url: RequestBuilder): Future[T] =
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
trait HttpClientReboot {
  self: JsonHttpClient =>
  override protected def getWithCreds(url: RequestBuilder): Future[String] =
      Http( url OK as.String)
}

/** implements caching using the supplied cache */
trait FSCaching {
  self: FSClientBase with JsonHttpClient =>

  class CacheKey(val req: RequestBuilder) {
    val url = req.build().getUrl()
    override def equals(o: Any): Boolean = o match {
      case o: CacheKey => o.url.equals(url)
      case _ => false
    }
    override def hashCode = {
      url.hashCode()
    }
  }

  val cache: LoadingCache[CacheKey, Future[String]]

  protected val loader: CacheLoader[CacheKey, Future[String]] =
    new CacheLoader[CacheKey, Future[String]]{
      def load(key: CacheKey) = getWithCreds(addParams(key.req))
  }

  override protected def getAndDeserialize[T](t: Class[T], url: RequestBuilder): Future[T] =
    for ( a <- cache.get(new CacheKey(url))) yield mapFromJson(t, a)
}

/** implements JSON mapping using Jackson, ignoring unknown properties */
trait JacksonMapper {
  self: JsonHttpClient =>
  override protected def mapFromJson[T](t: Class[T], json: String): T =
    JacksonMapper.mapper.readValue(json, t)
}

private object JacksonMapper {
  val mapper = newMapper
  def newMapper = {
    val m = new ObjectMapper()
    m.registerModule(DefaultScalaModule)
    m.registerModule(new JodaModule)
    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  }
}
