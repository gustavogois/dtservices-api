package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.model.builders.ProcessDTBuilder;
import com.gois.dtservicesapi.model.builders.RequesterBuilder;
import com.gois.dtservicesapi.respository.ProcessRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProcessController.class)
public class ProcessControllerTest {

    public static final String PROCESS_URI = "/process";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProcessRepository repository;

    @Test
    public void list() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROCESS_URI)
                .accept(MediaType.APPLICATION_JSON);

        Requester banco_abc = new RequesterBuilder().withId(1L).withName("Banco ABC").build();
        Requester banco_xyz = new RequesterBuilder().withId(2L).withName("Banco XYZ").build();
        List<Requester> requesters = Arrays.asList(banco_abc,banco_xyz);

        List<ProcessDT> process = Arrays.asList(
                new ProcessDTBuilder()
                        .withExtCode("adasd123123").withDtCreation(LocalDateTime.now().minusDays(1))
                        .withRequester(banco_abc).build(),
                new ProcessDTBuilder()
                        .withExtCode("jhkh234234").withDtCreation(LocalDateTime.now().minusDays(2))
                        .withRequester(banco_abc).build(),
                new ProcessDTBuilder()
                        .withExtCode("popoi098098").withDtCreation(LocalDateTime.now().minusDays(3))
                        .withRequester(banco_xyz).build(),
                new ProcessDTBuilder()
                        .withExtCode("qwqwqw23232").withDtCreation(LocalDateTime.now().minusDays(4))
                        .withRequester(banco_xyz).build()
        );

        when(repository.findAll()).thenReturn(process);

        MvcResult mvcResult = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].externalCode", is(process.get(0).getExternalCode())))
                .andReturn();

    }
}