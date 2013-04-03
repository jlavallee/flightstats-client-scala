package com.mobilerq.flightstats.api

import scala.language.implicitConversions
import com.mobilerq.flightstats.api.v1.ratings._
import com.mobilerq.flightstats.api.v1.flightstatus._
import dispatch.Defaults.executor
import scala.concurrent.Future

package object v1 {

  implicit def enrichAppendix(appendix: FSAppendix) =
    new RichAppendix(appendix)

  implicit def enrichFlightStatusResponseFuture(promise: Future[FSFlightStatusResponse]) =
    for(r <- promise) yield { new RichFlightStatusResponse(r) }

  implicit def enrichFlightStatusesResponseFuture(promise: Future[FSFlightStatusesResponse]) =
    for(r <- promise) yield { new RichFlightStatusesResponse(r) }

  implicit def enrichFlightTrackResponseFuture(promise: Future[FSFlightTrackResponse]) =
    for(r <- promise) yield { new RichFlightTrackResponse(r) }

  implicit def enrichFlightTracksResponseFuture(promise: Future[FSFlightTracksResponse]) =
    for(r <- promise) yield { new RichFlightTracksResponse(r) }

  implicit def enrichRatingsForRoute(f: Future[FSRatingsForRoute]) =
    for(r <- f) yield { new RichRatingsForRoute(r) }

  implicit def enrichRatingsForFlight(f: Future[FSRatingsForFlight]) =
    for(r <- f) yield { new RichRatingsForFlight(r) }
}