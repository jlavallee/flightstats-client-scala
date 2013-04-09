# flightstats-client-scala - a scala client for [FlightStats][1]

`flightstats-client-scala` makes use of [Dispatch][2] and [Jackson][3]
to provide a dead-simple asynchronous client for [FlightStats][1] API.

Each API is suppororted by a client class which provides methods for each
available endpoint.  Response objects are modeled by case classes, except
where the number of fields surpasses the case class limit.

## Example usage:

```scala
// create an instance of a client for the Airports API
val airports = FSAirports(appId, appKey)

// fetch active airports
val activeAirports:Future[Seq[FSAirport]] = airports.active


// create an instance of a client for the Delay Indexes API
val delayIndexes = FSDelayIndexes(appId, appKey)

// optional arguments passed as a map
val delayIndex:Future[FSDelayIndexResponse] =
  delayIndexes.byRegion("Caribbean", Map("classification" -> "3", "score" -> "3"))
```

## Organization:

Each [FlightStats][1] API has a corresponding class in `com.mobilerq.flightstats.client`:

  * `FSAirports` for the [Airports API](https://developer.flightstats.com/api-docs/airports/v1)
  * `FSAlerts` for the [Alerts API](https://developer.flightstats.com/api-docs/alerts/v1)
  * `FSDelayIndexes` for the [Delay Index API](https://developer.flightstats.com/api-docs/delayindex/v1)
  * `FSFlightStatusByAirport` for the [Flight Status & Track by Airport API](https://developer.flightstats.com/api-docs/flightstatus/v2/airport)
  * `FSFlightStatusByFlight` for the [Flight Status & Track by Flight API](https://developer.flightstats.com/api-docs/flightstatus/v2/flight)
  * `FSFlightStatusByRoute` for the [Flight Statys & Track by Route API](https://developer.flightstats.com/api-docs/flightstatus/v2/route)
  * `FSFlightsNear` for the [Flights Near API](https://developer.flightstats.com/api-docs/flightstatus/v2/flightsNear)
  * `FSRatings` for the [Ratings API](https://developer.flightstats.com/api-docs/ratings/v1)
  * `FSConnections` for the [Schedules/Connections API](https://developer.flightstats.com/api-docs/connections/v1)
  * `FSWeather` for the [Weather API](https://developer.flightstats.com/api-docs/weather/v1)

Responses are modeled as case classes wherever possible.  The three response objects that are not modeled as case classes (due the the restriction of a maximum of 22 fields) are `FSFlightLeg`, `FSRating` and `FSAirport`.

No method on any response object should ever return `null` - if one does, please submit a bug.

## Appendix Helpers:

Many of the [FlightStats][1] APIs return an appendix along with the response, containing
details about airports or airlines.  Users need to look up the details of an airport or
airline in the appendix (for example, to fetch the name).

`flightstats-client-scala` provies a few utilities for helping out with this.

The `RichFSAppendix` adds lookup methods to the appendix.  An implicit conversion from
`FSAppendix` -> `RichFSAppendix` is provided in the `com.mobilerq.flightstats.api.v1` package
object.  Example usage:

```scala
import com.mobilerq.flightstats.api.v1._   // get the implicit conversions

val response = statuses.flightStatus(285645279)

for(r <- response) yield {
  val carrierName = r.flightStatus.carrierFsCode flatMap { r.appendix.airlinesMap.get(_) }
  val arrivalAirportName = r.flightStatus.arrivalAirportFsCode flatMap { r.appendix.airportsMap.get(_) }
}
```

Looking things up in the appendix is still a bit of a pain, so there are rich response types
that can be used for greater convenience.  Implicit conversions are provided for most response
types that include an appendix.  Note that you must explicitly require the rich type on the
response, as the compiler cannot figure out that the future needs to be converted for you.  If
you can improve on this, please send a pull request!  Example usage:

```scala
import com.mobilerq.flightstats.api.v1._

val response: Future[RichFSFlightStatusResponse] = statuses.flightStatus(285645279)

for(r <- response) yield {
  val carrierName = r.flightStatus.carrier map {_.name}
  val arrivalAirportName = r.flightStatus.arrivalAirport map {_.name}
}
```

## Caching:

All clients support caching using a user supplied [guava][5] `CacheBuilder`.  All Client factories
accept a [guava][5] `CacheBuilder` as a third argument (after `appId` and `appKey`).  You can specify the cache properties (for example, the expiration policy) before constructing the client.

```scala
val cacheBuilder = CacheBuilder.newBuilder()
                               .expireAfterAccess(10, TimeUnit.MINUTES)
                               .maximumSize(1000)
                               .recordStats()

val client = FSFlightStatusByFlight(appId, appKey, cacheBuilder)

// do some stuff

val cacheStats = client.cacheStats  // NOTE you must have called .recordStats() on the builder in order for stats to have been collected
```

## Running the tests:

The tests run against static JSON test files captured from [FlightStats][1] API.  You can run the tests against [FlightStats][1] live API by supplying your credentials as properties:

```
mvn -Dflightstats.appId=<my appId> -Dflightstats.appKey=<my appKey> test
```

If you would like to capture new static JSON test files, add `-Dtest.capture=true`.

[1]: https://developer.flightstats.com/api-docs/
[2]: https://github.com/dispatch/reboot
[3]: http://jackson.codehaus.org
[4]: http://dispatch.databinder.net/Dispatch.html
[5]: https://code.google.com/p/guava-libraries/
