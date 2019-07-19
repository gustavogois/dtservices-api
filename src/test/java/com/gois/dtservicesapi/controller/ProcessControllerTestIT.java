package com.gois.dtservicesapi.controller;


import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.repository.ProcessRepository;
import com.gois.dtservicesapi.util.AbstractTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


// TODO: Transformar AbstractTest em TestUtil

// TODO: Logs

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "dev")
public class ProcessControllerTestIT extends AbstractTest {

    // TODO: Refactoring test classes, abstractIT

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private HttpHeaders headers;

    @Autowired
    private ProcessRepository repository;

    @Test
    public void create() throws Exception {

        ProcessDT processDT = repository.findById(1L).get();
        processDT.setId(null);


        HttpEntity<String> entity = new HttpEntity<String>(super.mapToJson(processDT), headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/process", port), HttpMethod.POST, entity, String.class);
        ProcessDT processCreated = super.mapFromJson(response.getBody().toString(), ProcessDT.class);
        assertThat(processCreated.getId()).isNotNull();
        assertThat(processCreated.getExternalCode()).isEqualTo(processDT.getExternalCode());
        assertThat(processCreated.getRequester()).isEqualTo(processDT.getRequester());
    }
}
