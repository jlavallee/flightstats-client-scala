package com.mobilerq.flightstats.client

import com.ning.http.client.RequestBuilder
import dispatch.Promise
import com.mobilerq.flightstats.api.v1.flightstatus._

trait FSFlightStatusHelpers extends FSClient {
  protected def status(url: RequestBuilder): Promise[RichFlightStatusResponse]  =
    getAndDeserialize(classOf[FSFlightStatusResponse], url)

  protected def statuses(url: RequestBuilder): Promise[RichFlightStatusesResponse] =
    getAndDeserialize(classOf[FSFlightStatusesResponse], url)
}

trait FSFlightTrackHelpers extends FSClient {
  protected def track(url: RequestBuilder): Promise[RichFlightTrackResponse] =
    getAndDeserialize(classOf[FSFlightTrackResponse], url)

  protected def tracks(url: RequestBuilder): Promise[RichFlightTracksResponse] =
    getAndDeserialize(classOf[FSFlightTracksResponse], url)
}