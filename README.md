# flightstats-client-scala - a scala client for [FlightStats][1]

## Example usage:

    :::scala
    // create an instance of a client for the Airports API
    val airports = FSAirports(appId, appKey)

    val activeAirports = airports.active


    :::scala
    // create an instance of a client for the Delay Indexes API
    val delayIndexes = FSDelayIndexes(appId, appKey)

    val delayIndex = delayIndexes.byRegion("Caribbean",
                         Map("classification" -> "3", "score" -> "3")
                     )

## Under the hood

`flightstats-client-scala` makes use of [reboot][2] and [Jackson][3]
to provide a dead-simple asynchronous client for [FlightStats][1] API.

See the excellent [Dispatch docs][4] for a primer on working with the
`Promises` returned by API methods.

[1]: https://developer.flightstats.com/api-docs/
[2]: https://github.com/dispatch/reboot
[3]: http://jackson.codehaus.org
[4]: http://dispatch.databinder.net/Dispatch.html
