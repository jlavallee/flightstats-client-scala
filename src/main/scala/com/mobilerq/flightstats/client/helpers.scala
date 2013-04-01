package com.mobilerq.flightstats.client

import com.ning.http.client.RequestBuilder
import dispatch.Promise
import com.mobilerq.flightstats.api.v1.flightstatus._

trait FSFlightStatusHelpers extends FSClient {
  protected def status(url: RequestBuilder): Promise[FSFlightStatusResponse]  =
    getAndDeserialize(classOf[FSFlightStatusResponse], url)

  protected def statuses(url: RequestBuilder): Promise[FSFlightStatusesResponse] =
    getAndDeserialize(classOf[FSFlightStatusesResponse], url)
}

trait FSFlightTrackHelpers extends FSClient {
  protected def track(url: RequestBuilder): Promise[FSFlightTrackResponse] =
    getAndDeserialize(classOf[FSFlightTrackResponse], url)

  protected def tracks(url: RequestBuilder): Promise[FSFlightTracksResponse] =
    getAndDeserialize(classOf[FSFlightTracksResponse], url)
}