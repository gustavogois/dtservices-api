package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.repository.filter.ProcessDTFilter;
import com.gois.dtservicesapi.service.ProcessDTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessDTService service;

    @GetMapping
    public List<ProcessDT> list() {

        return service.findAll();
    }

    @GetMapping("/search")
    public List<ProcessDT> search(ProcessDTFilter processFilter) {

        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<ProcessDT> create(@Valid @RequestBody ProcessDT process) {
        ProcessDT processSaved = service.save(process);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}").buildAndExpand(process.getId()).toUri();

        return ResponseEntity.created(uri).body(processSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessDT> getRequesterById(@PathVariable Long id) {
        Optional<ProcessDT> optProcess = service.findById(id);
        return optProcess.isPresent() ? ResponseEntity.ok(optProcess.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessDT> update(@PathVariable Long id, @Valid @RequestBody ProcessDT processDT) {

        return ResponseEntity.ok(service.update(id, processDT));
    }
}
