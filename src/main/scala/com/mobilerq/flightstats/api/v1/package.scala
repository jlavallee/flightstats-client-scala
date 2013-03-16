package com.mobilerq.flightstats.api

import scala.language.implicitConversions
import com.mobilerq.flightstats.api.v1.flightstatus._
import dispatch.Promise

package object v1 {

  implicit def enrichAppendix(appendix: FSAppendix) =
    new RichAppendix(appendix)

/*
  // unused
  implicit def enrichFlightStatusResponse(response: FSFlightStatusResponse) =
    new RichFlightStatusResponse(response)

  // doesn't work (needs appendix)
  implicit def enrichFlightStatus(status: FSFlightStatus) =
    new RichFlightStatus(status)
*/
  
  implicit def enrichFlightStatusResponsePromise(promise: Promise[FSFlightStatusResponse]) =
    for(r <- promise) yield { new RichFlightStatusResponse(r) }
}