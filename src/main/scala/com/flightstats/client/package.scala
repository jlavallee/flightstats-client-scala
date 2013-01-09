package com.flightstats

import com.flightstats.client.RequestVerbsWithDateHandling

import com.ning.http.client.RequestBuilder

/**
  * ===Client for FlightStats===
  *
  * This package provides client(s) for FlightStats APIs.
  *
  * Response objects are modeled in the [[com.flightstats.api]] package.
  *
  * ===Example usage===
  *
  * {{{
  * // create an instance of a client for the Airports API
  * val airports = FSAirports(appId, appKey)
  *
  * // fetch active airports
  * val activeAirports = airports.active
  *
  *
  * // create an instance of a client for the Delay Indexes API
  * val delayIndexes = FSDelayIndexes(appId, appKey)
  *
  * // optional arguments passed as a map
  * val delayIndex = delayIndexes.byRegion("Caribbean",
  *                      Map("classification" -> "3", "score" -> "3")
  *                  )
  * }}}
  *
  * ===Under the hood===
  *
  * This client makes use of Dispatch and Jackson
  * to provide a dead-simple asynchronous client for FlightStats API.
  *
  * See the excellent Dispatch docs for a primer on working with the
  * Promises returned by API methods.
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
  type ArgMap = Map[String, String]

  implicit def implyRequestVerbs(builder: RequestBuilder) =
    new RequestVerbsWithDateHandling(builder)
}
