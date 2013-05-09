package com.zeroclue.flightstats.api

import scala.language.implicitConversions
import com.zeroclue.flightstats.api.v1.ratings._
import com.zeroclue.flightstats.api.v1.connections._
import com.zeroclue.flightstats.api.v1.flightstatus._
import dispatch.Defaults.executor
import scala.concurrent.Future

package object v1 {

  implicit def enrichAppendix(appendix: FSAppendix) =
    new RichFSAppendix(appendix)

  implicit def enrichFlightStatusResponseFuture(future: Future[FSFlightStatusResponse]) =
    for(r <- future) yield { new RichFSFlightStatusResponse(r) }

  implicit def enrichFlightStatusesResponseFuture(future: Future[FSFlightStatusesResponse]) =
    for(r <- future) yield { new RichFSFlightStatusesResponse(r) }

  implicit def enrichFlightTrackResponseFuture(future: Future[FSFlightTrackResponse]) =
    for(r <- future) yield { new RichFSFlightTrackResponse(r) }

  implicit def enrichFlightTracksResponseFuture(future: Future[FSFlightTracksResponse]) =
    for(r <- future) yield { new RichFSFlightTracksResponse(r) }

  implicit def enrichRatingsForRoute(future: Future[FSRatingsForRoute]) =
    for(r <- future) yield { new RichFSRatingsForRoute(r) }

  implicit def enrichRatingsForFlight(future: Future[FSRatingsForFlight]) =
    for(r <- future) yield { new RichFSRatingsForFlight(r) }

  implicit def enrichConnections(future: Future[FSConnectionsResponse]) =
    for(r <- future) yield { new RichFSConnectionsResponse(r) }
}