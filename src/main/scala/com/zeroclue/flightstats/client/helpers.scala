package com.zeroclue.flightstats.client

import dispatch.Req
import com.zeroclue.flightstats.api.v1.flightstatus._
import scala.concurrent.Future

trait FSFlightStatusHelpers extends JsonHttpClient {
  protected def status(url: Req): Future[FSFlightStatusResponse]  =
    getAndDeserialize(classOf[FSFlightStatusResponse], url)

  protected def statuses(url: Req): Future[FSFlightStatusesResponse] =
    getAndDeserialize(classOf[FSFlightStatusesResponse], url)
}

trait FSFlightTrackHelpers extends JsonHttpClient {
  protected def track(url: Req): Future[FSFlightTrackResponse] =
    getAndDeserialize(classOf[FSFlightTrackResponse], url)

  protected def tracks(url: Req): Future[FSFlightTracksResponse] =
    getAndDeserialize(classOf[FSFlightTracksResponse], url)
}