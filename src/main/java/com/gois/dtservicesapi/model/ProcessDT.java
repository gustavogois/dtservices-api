package com.gois.dtservicesapi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
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

    private String internalCode;

    private LocalDate dtCreation;

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

    public LocalDate getDtCreation() {
        return dtCreation;
    }

    public void setDtCreation(LocalDate dtCreation) {
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
