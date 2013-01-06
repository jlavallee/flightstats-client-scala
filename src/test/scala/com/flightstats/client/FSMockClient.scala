package com.flightstats.client

import dispatch._
import com.ning.http.client.RequestBuilder
import io.Source
import java.io.File

trait FSMockClient extends FSClient with JacksonMapper {
  def startDir = Seq("src", "test", "resources")

  def getWithCreds(pathParts: String*): Promise[Either[Throwable, String]] = {
    val path = (startDir ++ apiLocation ++ pathParts.toSeq).foldLeft(".")(_ + File.separator + _)
    val json = Source.fromFile(path + ".json", "utf-8").mkString
    Promise(Right(json))
  }
}