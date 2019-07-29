package com.gois.dtservicesapi.repository.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ProcessDTFilter {

    private String internalCode;

    private String externalCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCreationFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCreationUntil;

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

    public LocalDateTime getDataCreationFrom() {
        return dataCreationFrom;
    }

    public void setDataCreationFrom(LocalDateTime dataCreationFrom) {
        this.dataCreationFrom = dataCreationFrom;
    }

    public LocalDateTime getDataCreationUntil() {
        return dataCreationUntil;
    }

    public void setDataCreationUntil(LocalDateTime dataCreationUntil) {
        this.dataCreationUntil = dataCreationUntil;
    }
}
