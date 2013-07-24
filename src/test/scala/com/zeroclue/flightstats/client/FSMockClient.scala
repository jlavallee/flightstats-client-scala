package com.zeroclue.flightstats.client

import dispatch.Req
import io.Source
import scala.concurrent.{CanAwait, Future, ExecutionContext, Await}
import scala.concurrent.duration.Duration
import scala.util.Try

trait FSMockClient extends FSClientBase
  with FSStaticTestJson
  with JacksonMapper {

  override protected def getWithCreds(url: Req): Future[String] =
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
