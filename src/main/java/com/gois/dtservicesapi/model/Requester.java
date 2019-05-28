package com.gois.dtservicesapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "requester")
public class Requester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String dataBilling;

    private String acronym;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataBilling() {
        return dataBilling;
    }

    public void setDataBilling(String dataBilling) {
        this.dataBilling = dataBilling;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requester requester = (Requester) o;
        return id.equals(requester.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
