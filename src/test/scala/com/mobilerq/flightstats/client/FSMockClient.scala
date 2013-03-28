package com.mobilerq.flightstats.client

import dispatch.Promise
import com.ning.http.client.RequestBuilder
import io.Source
import scala.concurrent.{CanAwait, Future, ExecutionContext}
import scala.util.Try
import scala.concurrent.duration.Duration
import scala.concurrent.Await

trait FSMockClient extends FSClientBase with FSStaticTestJson {

  override protected def getWithCreds(url: RequestBuilder): Future[String] = 
    new Future[String] {
      private val contents = Try(Source.fromFile(filePathForUrl(url), "utf-8").mkString)
      override def value = Some(contents)
      override def isCompleted = true
      override def ready(atMost: Duration)(implicit permit: CanAwait) = this
      override def result(atMost: Duration)(implicit permit: CanAwait) = contents.getOrElse("")
      override def onComplete[U](func: Try[String] => U)(implicit executor: ExecutionContext) {
        func(contents)
      }
  }
}