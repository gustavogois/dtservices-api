package com.gois.dtservicesapi.repository;

import com.gois.dtservicesapi.model.Requester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProcessRepositoryTest {

    @Autowired
    ProcessRepository repository;

    @Autowired
    RequesterRepository requesterRepository;

    @Test
    public void getQuantityOfRequesterByProcessTest() {

        Requester requester = requesterRepository.getOne(1L);
        Requester requester_not_found = requesterRepository.getOne(1000L);

        int quant = repository.getQuantityOfRequesterByProcess(1L, requester);
        assertThat(quant).isEqualTo(1);

        int quant_not_found = repository.getQuantityOfRequesterByProcess(1L, requester_not_found);
        assertThat(quant_not_found).isEqualTo(0);
    }

}