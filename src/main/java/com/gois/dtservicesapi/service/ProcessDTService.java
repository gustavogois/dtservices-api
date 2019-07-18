package com.gois.dtservicesapi.service;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessDTService {

    @Autowired
    private ProcessRepository repository;

    public List<ProcessDT> findAll() {
        return repository.findAll();
    }

    public ProcessDT save(ProcessDT process) {
        return repository.save(process);
    }
}
