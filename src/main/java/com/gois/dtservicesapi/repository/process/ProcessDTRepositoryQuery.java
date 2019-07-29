package com.gois.dtservicesapi.repository.process;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.repository.filter.ProcessDTFilter;

import java.util.List;

public interface ProcessDTRepositoryQuery {
    public List<ProcessDT> filter(ProcessDTFilter processDTFilter);
}
