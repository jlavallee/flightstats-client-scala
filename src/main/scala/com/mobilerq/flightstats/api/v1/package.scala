package com.mobilerq.flightstats.api

import scala.language.implicitConversions
import com.mobilerq.flightstats.api.v1.flightstatus._
import dispatch.Promise

package object v1 {

  implicit def enrichAppendix(appendix: FSAppendix) =
    new RichAppendix(appendix)

  implicit def enrichFlightStatusResponsePromise(promise: Promise[FSFlightStatusResponse]) =
    for(r <- promise) yield { new RichFlightStatusResponse(r) }

  implicit def enrichFlightStatusesResponsePromise(promise: Promise[FSFlightStatusesResponse]) =
    for(r <- promise) yield { new RichFlightStatusesResponse(r) }

  implicit def enrichFlightTrackResponsePromise(promise: Promise[FSFlightTrackResponse]) =
    for(r <- promise) yield { new RichFlightTrackResponse(r) }

  implicit def enrichFlightTracksResponsePromise(promise: Promise[FSFlightTracksResponse]) =
    for(r <- promise) yield { new RichFlightTracksResponse(r) }
}