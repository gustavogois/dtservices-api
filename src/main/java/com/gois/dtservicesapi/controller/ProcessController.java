package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.service.ProcessDTService;
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
    private ProcessDTService service;

    @GetMapping
    public List<ProcessDT> list() {

        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<ProcessDT> create(@Valid @RequestBody ProcessDT process) {
        ProcessDT processSaved = service.save(process);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}").buildAndExpand(process.getId()).toUri();

        return ResponseEntity.created(uri).body(processSaved);
    }

    // TODO: Acabar o Controller

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
