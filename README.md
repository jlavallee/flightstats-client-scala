# flightstats-client-scala - a scala client for [FlightStats][1]

`flightstats-client-scala` makes use of [Dispatch][2] and [Jackson][3]
to provide a dead-simple asynchronous client for [FlightStats][1] API.

See the excellent [Dispatch docs][4] for a primer on working with the
`Promises` returned by API methods.

Each API is suppororted by a client class which provides methods for each
available endpoint.  Response objects are modeled by case classes, except
where the number of fields surpasses the case class limit.

## Example usage:

```scala
// create an instance of a client for the Airports API
val airports = FSAirports(appId, appKey)

// fetch active airports
val activeAirports:Promise[Seq[FSAirport]] = airports.active

// to handle errors gracefully, use .either (see Dispatch docs)
val activeAirportsEither:Promise[Either[Throwable, Seq[FSAirport]]] = airports.active.either


// create an instance of a client for the Delay Indexes API
val delayIndexes = FSDelayIndexes(appId, appKey)

// optional arguments passed as a map
val delayIndex:Promise[FSDelayIndexResponse] =
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

[1]: https://developer.flightstats.com/api-docs/
[2]: https://github.com/dispatch/reboot
[3]: http://jackson.codehaus.org
[4]: http://dispatch.databinder.net/Dispatch.html
