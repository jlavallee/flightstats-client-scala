package com.flightstats

import com.flightstats.client.RequestVerbsWithDateHandling
import com.ning.http.client.RequestBuilder
package object client {
  implicit def implyRequestVerbs(builder: RequestBuilder) =
    new RequestVerbsWithDateHandling(builder)
}
