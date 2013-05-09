package com.zeroclue.flightstats.client

import com.ning.http.client.RequestBuilder
import com.zeroclue.flightstats.api.v1.flightstatus._
import scala.concurrent.Future

trait FSFlightStatusHelpers extends FSClient {
  protected def status(url: RequestBuilder): Future[FSFlightStatusResponse]  =
    getAndDeserialize(classOf[FSFlightStatusResponse], url)

  protected def statuses(url: RequestBuilder): Future[FSFlightStatusesResponse] =
    getAndDeserialize(classOf[FSFlightStatusesResponse], url)
}

trait FSFlightTrackHelpers extends FSClient {
  protected def track(url: RequestBuilder): Future[FSFlightTrackResponse] =
    getAndDeserialize(classOf[FSFlightTrackResponse], url)

  protected def tracks(url: RequestBuilder): Future[FSFlightTracksResponse] =
    getAndDeserialize(classOf[FSFlightTracksResponse], url)
}