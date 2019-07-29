package com.gois.dtservicesapi.model.builders;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.model.Requester;

import java.time.LocalDate;


public class ProcessDTBuilder {

    private ProcessDT process = new ProcessDT();

    public ProcessDT build() {
        return this.process;
    }

    public ProcessDTBuilder withId(Long id) {
        this.process.setId(id);
        return this;
    }
    public ProcessDTBuilder withExtCode(String extCode) {
        this.process.setExternalCode(extCode);
        return this;
    }
    public ProcessDTBuilder withDtCreation(LocalDate dtCreation) {
        this.process.setDtCreation(dtCreation);
        return this;
    }
    public ProcessDTBuilder withRequester(Requester requester) {
        this.process.setRequester(requester);
        return this;
    }
}
