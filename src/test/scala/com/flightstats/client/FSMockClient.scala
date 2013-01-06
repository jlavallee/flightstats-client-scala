package com.flightstats.client

import dispatch._
import com.ning.http.client.RequestBuilder
import io.Source
import java.io.File

trait FSMockClient extends FSClient with JacksonMapper {
  def startDir = "src/test/resources/"

  def getWithCreds(url: RequestBuilder): Promise[Either[Throwable, String]] = {
    // could use addParams(url) if we wanted out .json file paths to include our query params
    val queryPathWithHost = url.build().getRawUrl().split("://")(1)
    val path = startDir + queryPathWithHost.substring(queryPathWithHost.indexOf("/"))
    val json = Source.fromFile(path + ".json", "utf-8").mkString
    Promise(Right(json))
  }
}