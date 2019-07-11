package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.respository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessRepository repository;

    //@Autowired
    //private RequesterService service;

    @GetMapping
    public List<ProcessDT> list() {

        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<ProcessDT> create(@Valid @RequestBody ProcessDT process) {
        ProcessDT processSaved = repository.save(process);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}").buildAndExpand(process.getId()).toUri();

        return ResponseEntity.created(uri).body(processSaved);
    }

    /*

    @GetMapping("/{id}")
    public ResponseEntity<ProcessDT> getRequesterById(@PathVariable Long id) {
        Optional<ProcessDT> optRequester = repository.findById(id);
        return optRequester.isPresent() ? ResponseEntity.ok(optRequester.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Requester> update(@PathVariable Long id, @Valid @RequestBody Requester requester) {

        return ResponseEntity.ok(service.update(id, requester));
    }*/
}
