package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.respository.RequesterRepository;
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

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RequesterController.class)
public class RequesterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RequesterRepository repositoryMock;

    @Test
    public void list() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/requesters")
                .accept(MediaType.APPLICATION_JSON);

        when(repositoryMock.findAll())
                .thenReturn(singletonList(new Requester()));

        MvcResult mvcResult = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                //.andExpect(content().json("{id: 1, name: Gustavo, price: 10, quantity: 20}"))
                .andReturn();


    }

    @Test
    public void create() {
    }

    @Test
    public void getRequesterById() {
    }

    @Test
    public void remover() {
    }
}