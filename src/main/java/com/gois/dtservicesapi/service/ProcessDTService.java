package com.gois.dtservicesapi.service;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.repository.ProcessRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// TODO: Unit tests

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

    public Optional<ProcessDT> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ProcessDT update(Long id, ProcessDT processDT) {
        Optional<ProcessDT> optProcess = repository.findById(id);
        ProcessDT processBD = optProcess.orElseThrow(() -> new EmptyResultDataAccessException(1));
        BeanUtils.copyProperties(processDT, processBD, "id");
        return repository.save(processBD);
    }
}
