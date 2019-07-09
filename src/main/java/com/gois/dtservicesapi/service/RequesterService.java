package com.gois.dtservicesapi.service;

import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.respository.RequesterRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequesterService {

    @Autowired
    private RequesterRepository repository;


    public Requester update(Long id, Requester requester) {
        Optional<Requester> optRequester = repository.findById(id);
        Requester requesterBD = optRequester.orElseThrow(() -> new EmptyResultDataAccessException(1));
        BeanUtils.copyProperties(requester, requesterBD, "id");
        return repository.save(requesterBD);
    }
}
