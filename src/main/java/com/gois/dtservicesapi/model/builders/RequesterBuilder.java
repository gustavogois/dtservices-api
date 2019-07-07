package com.gois.dtservicesapi.model.builders;

import com.gois.dtservicesapi.model.Requester;

public class RequesterBuilder {

    private Requester requester = new Requester();

    public RequesterBuilder withName(String name) {
        this.requester.setName(name);
        return this;
    }

    public Requester build() {
        return this.requester;
    }
}
