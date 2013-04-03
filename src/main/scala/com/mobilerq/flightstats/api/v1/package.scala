package com.mobilerq.flightstats.api

import scala.language.implicitConversions
import com.mobilerq.flightstats.api.v1.ratings._
import com.mobilerq.flightstats.api.v1.flightstatus._
import dispatch.Defaults.executor
import scala.concurrent.Future

package object v1 {

  implicit def enrichAppendix(appendix: FSAppendix) =
    new RichAppendix(appendix)

  implicit def enrichFlightStatusResponseFuture(future: Future[FSFlightStatusResponse]) =
    for(r <- future) yield { new RichFlightStatusResponse(r) }

  implicit def enrichFlightStatusesResponseFuture(future: Future[FSFlightStatusesResponse]) =
    for(r <- future) yield { new RichFlightStatusesResponse(r) }

  implicit def enrichFlightTrackResponseFuture(future: Future[FSFlightTrackResponse]) =
    for(r <- future) yield { new RichFlightTrackResponse(r) }

  implicit def enrichFlightTracksResponseFuture(future: Future[FSFlightTracksResponse]) =
    for(r <- future) yield { new RichFlightTracksResponse(r) }

  implicit def enrichRatingsForRoute(future: Future[FSRatingsForRoute]) =
    for(r <- future) yield { new RichRatingsForRoute(r) }

  implicit def enrichRatingsForFlight(future: Future[FSRatingsForFlight]) =
    for(r <- future) yield { new RichRatingsForFlight(r) }
}