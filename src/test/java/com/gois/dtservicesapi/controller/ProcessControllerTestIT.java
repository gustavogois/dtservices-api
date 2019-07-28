package com.gois.dtservicesapi.controller;


import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.repository.ProcessRepository;
import com.gois.dtservicesapi.util.AbstractTest;
import com.gois.dtservicesapi.util.data.ProcessData;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "dev")
public class ProcessControllerTestIT extends AbstractTest {

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

        long count = repository.count();

        HttpEntity<String> entity = new HttpEntity<String>(super.mapToJson(processDT), headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/process", port), HttpMethod.POST, entity, String.class);
        ProcessDT processCreated = super.mapFromJson(response.getBody().toString(), ProcessDT.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(repository.count()).isEqualTo(count + 1);
        assertThat(processCreated.getId()).isNotNull();
        assertThat(processCreated.getExternalCode()).isEqualTo(processDT.getExternalCode());
        assertThat(processCreated.getRequester()).isEqualTo(processDT.getRequester());
    }

    @Test
    public void create_with_requester_not_found() throws Exception {

        HttpEntity<String> entity = new HttpEntity<String>(ProcessData.getProcessWithRequesterNotFoundJSON(), headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/process", port), HttpMethod.POST, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void findById() throws Exception {

        String id = "1";

        ResponseEntity<String> response = restTemplate.getForEntity(
                createURLWithPort("/process/" + id, port), String.class);
        ProcessDT process = super.mapFromJson(response.getBody().toString(), ProcessDT.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(process.getId()).isEqualTo(Long.parseLong(id));
    }

    @Test
    public void update() throws Exception {

        String id = "1";

        ResponseEntity<String> response = restTemplate.getForEntity(
                createURLWithPort("/process/" + id, port), String.class);
        ProcessDT process = super.mapFromJson(response.getBody().toString(), ProcessDT.class);
        process.setExternalCode("new");
        HttpEntity<String> entity = new HttpEntity<String>(super.mapToJson(process), headers);
        ResponseEntity<String> responseUpdate = restTemplate.exchange(
                createURLWithPort("/process/" + id, port), HttpMethod.PUT, entity, String.class);
        ProcessDT processUpdated = super.mapFromJson(responseUpdate.getBody().toString(), ProcessDT.class);
        assertThat(processUpdated.getExternalCode()).isEqualTo("new");
        assertThat(processUpdated.getId()).isEqualTo(process.getId());
        assertThat(processUpdated.getInternalCode()).isEqualTo(process.getInternalCode());
    }

    @Test
    public void deleteById() throws Exception {

        String id = "2";

        long count = repository.count();

        restTemplate.delete(createURLWithPort("/process/" + id, port));
        assertThat(repository.count()).isEqualTo(count - 1);
    }
}
