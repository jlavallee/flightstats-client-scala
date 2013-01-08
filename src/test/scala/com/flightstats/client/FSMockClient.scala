package com.flightstats.client

import dispatch.Promise
import com.ning.http.client.RequestBuilder
import io.Source
import java.io.File
import scala.concurrent.{future, promise}

trait FSMockClient extends FSClient with JacksonMapper with FSStaticTestJson {

  def getWithCreds(url: RequestBuilder): Promise[String] = {
    // could use addParams(url) if we wanted out .json file paths to include our query params

    new Promise[String] {
      def claim = Source.fromFile(filePathForUrl(url), "utf-8").mkString
      def replay = this
      def isComplete = true
      val http = null
      def addListener(f: () => Unit) = f()
    }
  }
}