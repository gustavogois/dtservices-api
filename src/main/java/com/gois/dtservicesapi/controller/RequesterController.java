package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.respository.RequesterRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/requesters")
public class RequesterController {

    @Autowired
    private RequesterRespository repository;

    @GetMapping
    public List<Requester> list() {

        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Requester> create(@RequestBody Requester requester) {
        Requester requesterSaved = repository.save(requester);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}").buildAndExpand(requester.getId()).toUri();

        return ResponseEntity.created(uri).body(requesterSaved);
    }

    @GetMapping("/{id}")
    public Requester getRequesterById(@PathVariable Long id) {
        return repository.findById(id).orElse(new Requester());
    }
}
