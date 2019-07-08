package com.gois.dtservicesapi.model.builders;

import com.gois.dtservicesapi.model.Requester;

public class RequesterBuilder {

    private Requester requester = new Requester();

    public RequesterBuilder withId(Long id) {
        this.requester.setId(id);
        return this;
    }

    public RequesterBuilder withName(String name) {
        this.requester.setName(name);
        return this;
    }

    public RequesterBuilder withDataBilling(String dataBilling) {
        this.requester.setDataBilling(dataBilling);
        return this;
    }

    public RequesterBuilder withAcronym(String acronym) {
        this.requester.setAcronym(acronym);
        return this;
    }

    public Requester build() {
        return this.requester;
    }
}
