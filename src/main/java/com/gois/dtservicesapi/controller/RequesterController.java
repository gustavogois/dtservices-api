package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.respository.RequesterRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
