package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.respository.RequesterRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/requesters")
public class RequesterController {

    @Autowired
    private RequesterRepository repository;

    @GetMapping
    public List<Requester> list() {

        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Requester> create(@Valid @RequestBody Requester requester) {
        Requester requesterSaved = repository.save(requester);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}").buildAndExpand(requester.getId()).toUri();

        return ResponseEntity.created(uri).body(requesterSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Requester> getRequesterById(@PathVariable Long id) {
        Optional<Requester> optRequester = repository.findById(id);
        return optRequester.isPresent() ? ResponseEntity.ok(optRequester.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Requester> update(@PathVariable Long id, @Valid @RequestBody Requester requester) {
        Optional<Requester> optRequester = repository.findById(id);
        Requester requesterBD = optRequester.orElseThrow(() -> new EmptyResultDataAccessException(1));
        BeanUtils.copyProperties(requester, requesterBD, "id");
        repository.save(requesterBD);
        return ResponseEntity.ok(requesterBD);
    }
}
