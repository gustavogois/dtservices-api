package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.respository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessRepository process;

    //@Autowired
    //private RequesterService service;

    @GetMapping
    public List<ProcessDT> list() {

        return process.findAll();
    }

    /*

    @PostMapping
    public ResponseEntity<Requester> create(@Valid @RequestBody Requester requester) {
        Requester requesterSaved = process.save(requester);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}").buildAndExpand(requester.getId()).toUri();

        return ResponseEntity.created(uri).body(requesterSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessDT> getRequesterById(@PathVariable Long id) {
        Optional<ProcessDT> optRequester = process.findById(id);
        return optRequester.isPresent() ? ResponseEntity.ok(optRequester.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        process.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Requester> update(@PathVariable Long id, @Valid @RequestBody Requester requester) {

        return ResponseEntity.ok(service.update(id, requester));
    }*/
}
