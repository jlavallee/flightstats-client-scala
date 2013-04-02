package com.mobilerq.flightstats.api

import scala.language.implicitConversions

package object v1 {

  implicit def enrichAppendix(appendix: FSAppendix) =
    new RichAppendix(appendix)

}