package com.flightstats.client

import dispatch.Promise
import com.ning.http.client.RequestBuilder
import io.Source
import scala.concurrent.{future, promise}

trait FSMockClient extends FSClientBase with FSStaticTestJson {

  override protected def getWithCreds(url: RequestBuilder): Promise[String] = {
    new Promise[String] {
      def claim = Source.fromFile(filePathForUrl(url), "utf-8").mkString
      def replay = this
      def isComplete = true
      val http = null
      def addListener(f: () => Unit) = f()
    }
  }
}