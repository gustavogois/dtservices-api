package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.model.builders.RequesterBuilder;
import com.gois.dtservicesapi.respository.RequesterRepository;
import com.gois.dtservicesapi.util.AbstractTest;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RequesterController.class)
public class RequesterControllerTest extends AbstractTest {

    public static final String REQUESTERS_URI = "/requesters";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RequesterRepository repositoryMock;

    @Test
    public void list() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(REQUESTERS_URI)
                .accept(MediaType.APPLICATION_JSON);

        List<Requester> requesters = Arrays.asList(
                new RequesterBuilder().withName("Banco ABC").build(),
                new RequesterBuilder().withName("Banco XYZ").build()
        );

        when(repositoryMock.findAll())
                .thenReturn(requesters);

        MvcResult mvcResult = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(requesters.get(0).getName())))
                .andReturn();


    }

    @Test
    public void create_400_acronym_required() throws Exception {
        Requester banco_abc = new RequesterBuilder().withName("Banco ABC").build();

        String inputJson = super.mapToJson(banco_abc);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                .post(REQUESTERS_URI)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(inputJson))
                                        .andExpect(status().isBadRequest())
                                        .andReturn();

    }

    @Test
    public void create() throws Exception {

        Requester banco_abc = new RequesterBuilder().withName("Banco ABC").withAcronym("ABC").build();

        when(repositoryMock.save(banco_abc)).thenReturn(banco_abc);

        String inputJson = super.mapToJson(banco_abc);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post(REQUESTERS_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andExpect(status().isCreated())
                .andReturn();

        Requester requester = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Requester.class);
        assertThat(requester).isNotNull();
        assertThat(requester.getAcronym()).isEqualTo("ABC");
    }

    @Test
    public void getRequesterById_notfound() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(REQUESTERS_URI + "/23")
                .accept(MediaType.APPLICATION_JSON);

        when(repositoryMock.findById(any())).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    public void getRequesterById() throws Exception {
        Requester banco_abc = new RequesterBuilder().withName("Banco ABC").withAcronym("ABC").build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(REQUESTERS_URI + "/23")
                .accept(MediaType.APPLICATION_JSON);

        when(repositoryMock.findById(any())).thenReturn(Optional.of(banco_abc));

        MvcResult mvcResult = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();

        Requester requester = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Requester.class);
        assertThat(requester).isNotNull();
        assertThat(requester.getAcronym()).isEqualTo("ABC");
    }

    @Test
    public void remover() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUESTERS_URI + "/23"))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void update() throws Exception {
        Requester banco_abc_with_no_data_billing = new RequesterBuilder()
                                                            .withName("Banco ABC").withAcronym("ABC").build();
        when(repositoryMock.findById(any())).thenReturn(Optional.of(banco_abc_with_no_data_billing));
        Requester banco_abc_with_data_billing = new RequesterBuilder()
                .withName("Banco ABC").withAcronym("ABC").withDataBilling("Dados para faturamento").build();

        String inputJson = super.mapToJson(banco_abc_with_data_billing);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put(REQUESTERS_URI + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andExpect(status().isOk())
                .andReturn();

        Requester requester = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Requester.class);
        assertThat(requester).isNotNull();
        assertThat(requester.getDataBilling()).isEqualTo("Dados para faturamento");
    }
}