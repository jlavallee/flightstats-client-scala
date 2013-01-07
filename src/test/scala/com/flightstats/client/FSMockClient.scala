package com.flightstats.client

import dispatch._
import com.ning.http.client.RequestBuilder
import io.Source
import java.io.File

trait FSMockClient extends FSClient with JacksonMapper with FSStaticTestJson {

  def getWithCreds(url: RequestBuilder): Promise[Either[Throwable, String]] = {
    // could use addParams(url) if we wanted out .json file paths to include our query params
    val json = Source.fromFile(filePathForUrl(url), "utf-8").mkString
    Promise(Right(json))
  }
}