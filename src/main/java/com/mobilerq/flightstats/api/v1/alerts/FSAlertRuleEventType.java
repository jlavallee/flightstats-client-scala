package com.mobilerq.flightstats.api.v1.alerts;

import scala.Option;

public enum FSAlertRuleEventType {
    PRE_DEPARTURE_STATUS("preDep"),
    PRE_ARRIVAL_STATUS("preArr"),
    DEPARTED_LATE("depLate"),
    ARRIVED_LATE("arrLate"),
    EN_ROUTE("dep"),
    LANDED("arr"),
    CANCELLED("can"),
    DIVERTED("div"),
    DEPARTURE_DELAY("depDelay"),
    ARRIVAL_DELAY("arrDelay"),
    DEPARTURE_GATE("depGate"),
    ARRIVAL_GATE("arrGate"),
    BAGGAGE("bag"),
    TIME_ADJUSTMENT(null),
    UNKNOWN(null),
    ALL_CHANGES(null);

    private Option<String> triggerCode;

    private FSAlertRuleEventType(String triggerCode){
        this.triggerCode = triggerCode == null
                         ? Option.<String>empty()
                         : Option.apply(triggerCode);
    }

    public Option<String> triggerCode(){
        return this.triggerCode;
    }
}
