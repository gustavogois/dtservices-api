package com.gois.dtservicesapi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "process")
public class ProcessDT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    private String externalCode;

    @NotNull
    @Size(min = 7, max = 7)
    private String internalCode;

    @NotNull
    private Date dtCreation;

    @ManyToOne
    private Requester requester;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public Date getDtCreation() {
        return dtCreation;
    }

    public void setDtCreation(Date dtCreation) {
        this.dtCreation = dtCreation;
    }

    public Requester getRequester() {
        return requester;
    }

    public void setRequester(Requester requester) {
        this.requester = requester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessDT processDT = (ProcessDT) o;
        return Objects.equals(id, processDT.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
