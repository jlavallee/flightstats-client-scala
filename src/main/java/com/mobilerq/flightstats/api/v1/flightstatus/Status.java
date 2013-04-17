package com.mobilerq.flightstats.api.v1.flightstatus;

public enum Status {
    A("Active"),
    C("Canceled"),
    D("Diverted"),
    DN("Data source needed"),
    L("Landed"),
    NO("Not Operational"),
    R("Redirected"),
    S("Scheduled"),
    U("Unknown");

    private final String description;
    Status(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
