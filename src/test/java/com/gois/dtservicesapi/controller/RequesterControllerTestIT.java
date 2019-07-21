package com.gois.dtservicesapi.controller;


import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.repository.RequesterRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "dev")
public class RequesterControllerTestIT extends AbstractTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private HttpHeaders headers;

    @Autowired
    private RequesterRepository repository;

    @Test
    public void create() throws Exception {

        Requester requester = repository.findById(1L).get();
        requester.setId(null);

        long count = repository.count();

        HttpEntity<String> entity = new HttpEntity<String>(super.mapToJson(requester), headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/requesters", port), HttpMethod.POST, entity, String.class);
        Requester requesterCreated = super.mapFromJson(response.getBody().toString(), Requester.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(repository.count()).isEqualTo(count + 1);
        assertThat(requesterCreated.getId()).isNotNull();
        assertThat(requesterCreated.getAcronym()).isEqualTo(requester.getAcronym());
        assertThat(requesterCreated.getName()).isEqualTo(requester.getName());
    }

    @Test
    public void findById() throws Exception {

        String id = "1";

        ResponseEntity<String> response = restTemplate.getForEntity(
                createURLWithPort("/requesters/" + id, port), String.class);
        Requester requester = super.mapFromJson(response.getBody().toString(), Requester.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(requester.getId()).isEqualTo(Long.parseLong(id));
    }

    @Test
    public void update() throws Exception {

        String id = "1";

        ResponseEntity<String> response = restTemplate.getForEntity(
                createURLWithPort("/requesters/" + id, port), String.class);
        Requester requester = super.mapFromJson(response.getBody().toString(), Requester.class);
        requester.setName("new");
        HttpEntity<String> entity = new HttpEntity<String>(super.mapToJson(requester), headers);
        ResponseEntity<String> responseUpdate = restTemplate.exchange(
                createURLWithPort("/requesters/" + id, port), HttpMethod.PUT, entity, String.class);
        Requester requesterUpdated = super.mapFromJson(responseUpdate.getBody().toString(), Requester.class);
        assertThat(requesterUpdated.getName()).isEqualTo("new");
        assertThat(requesterUpdated.getId()).isEqualTo(requester.getId());
        assertThat(requesterUpdated.getAcronym()).isEqualTo(requester.getAcronym());
    }

    @Test
    public void deleteById() throws Exception {

        // TODO: Excluir Requester com Process associado

        /*
        String id = "2";

        long count = repository.count();

        restTemplate.delete(createURLWithPort("/requesters/" + id, port));
        assertThat(repository.count()).isEqualTo(count - 1);
        */
    }
}
