package com.zeroclue.flightstats

import scala.language.implicitConversions
import dispatch.Req

/**
  * ===Client for FlightStats===
  *
  * This package provides client(s) for FlightStats APIs.
  *
  * Response objects are modeled in the [[com.zeroclue.flightstats.api]] package.
  *
  * ===Example usage===
  *
  * {{{
  * // create an instance of a client for the Airports API
  * val airports = FSAirports(appId, appKey)
  *
  * // fetch active airports
  * val activeAirports:Future[Seq[FSAirport]] = airports.active
  *
  * // to handle errors gracefully, use .either (see Dispatch docs)
  * val activeAirportsEither:Future[Either[Throwable, Seq[FSAirport]]] = airports.active.either
  *
  *
  * // create an instance of a client for the Delay Indexes API
  * val delayIndexes = FSDelayIndexes(appId, appKey)
  *
  * // optional arguments passed as a map
  * val delayIndex:Future[FSDelayIndexResponse] =
  *   delayIndexes.byRegion("Caribbean", Map("classification" -> "3", "score" -> "3"))
  * }}}
  *
  * ===Under the hood===
  *
  * This client makes use of Dispatch and Jackson
  * to provide a dead-simple asynchronous client for FlightStats API.
  *
  *
  * @see
  *  <ul>
  *    <li><a target="_top" href="https://developer.flightstats.com/api-docs/">FlightStats API Documentation</a>
  *    <li><a target="_top" href="http://jackson.codehaus.org">Jackson</a>
  *    <li><a target="_top" href="http://dispatch.databinder.net/Dispatch.html">Dispatch Documentation</a>
  *    <li><a target="_top" href="https://github.com/dispatch/reboot">Dispatch on github</a>
  *  </ul>
  *
  */
package object client {
  type ArgMap = Traversable[(String, String)]

  implicit def implyRequestVerbs(builder: Req) =
    new EnhancedRequestVerbs(builder)
}
