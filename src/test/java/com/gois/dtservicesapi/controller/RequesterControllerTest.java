package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.model.builders.RequesterBuilder;
import com.gois.dtservicesapi.repository.RequesterRepository;
import com.gois.dtservicesapi.service.RequesterService;
import com.gois.dtservicesapi.util.AbstractTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
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
    RequesterRepository repository;

    @MockBean
    RequesterService service;

    private static List<Requester> requesters;
    private static final int BANCO_ABC_WITH_NO_DATA_BILLING = 0;
    private static final int BANCO_XYZ = 1;
    private static final int WITHOUT_ACRONYM = 2;
    private static final int BANCO_ABC_WITH_DATA_BILLING = 3;
    private static final int WITH_NAME_NULL = 4;

    @BeforeClass
    public static void setUp() throws Exception {
        Requester banco_abc = new RequesterBuilder().withId(1L).withName("Banco ABC").withAcronym("ABC").build();
        Requester banco_xyz = new RequesterBuilder().withId(2L).withName("Banco XYZ").withAcronym("XYZ").build();
        Requester without_acronym = new RequesterBuilder().withName("Banco ABC").build();
        Requester banco_abc_with_data_billing = new RequesterBuilder()
                .withName("Banco ABC").withAcronym("ABC").withDataBilling("Dados para faturamento").build();
        Requester with_name_null = new RequesterBuilder()
                .withAcronym("ABC").withDataBilling("Dados para faturamento").build();
        requesters = Arrays.asList(banco_abc, banco_xyz, without_acronym, banco_abc_with_data_billing, with_name_null);
    }

        @Test
    public void list() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(REQUESTERS_URI)
                .accept(MediaType.APPLICATION_JSON);

        when(repository.findAll())
                .thenReturn(this.requesters);

        MvcResult mvcResult = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(requesters.size())))
                .andExpect(jsonPath("$[0].name", is(this.requesters.get(BANCO_ABC_WITH_NO_DATA_BILLING).getName())))
                .andReturn();
    }

    @Test
    public void create_400_acronym_required() throws Exception {

        String inputJson = super.mapToJson(this.requesters.get(WITHOUT_ACRONYM));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                .post(REQUESTERS_URI)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(inputJson))
                                        .andExpect(status().isBadRequest())
                                        .andReturn();

    }

    @Test
    public void create() throws Exception {

        when(repository.save(requesters.get(BANCO_ABC_WITH_NO_DATA_BILLING))).thenReturn(requesters.get(BANCO_ABC_WITH_NO_DATA_BILLING));

        String inputJson = super.mapToJson(requesters.get(BANCO_ABC_WITH_NO_DATA_BILLING));
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

        when(repository.findById(any())).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    public void getRequesterById() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(REQUESTERS_URI + "/23")
                .accept(MediaType.APPLICATION_JSON);

        when(repository.findById(any())).thenReturn(Optional.of(requesters.get(BANCO_ABC_WITH_NO_DATA_BILLING)));

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
        when(service.update(1L, requesters.get(BANCO_ABC_WITH_NO_DATA_BILLING)))
                .thenReturn(requesters.get(BANCO_ABC_WITH_DATA_BILLING));

        String inputJson = super.mapToJson(requesters.get(BANCO_ABC_WITH_NO_DATA_BILLING));
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

    @Test
    public void update_name_null() throws Exception {


        String inputJson = super.mapToJson(requesters.get(WITH_NAME_NULL));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put(REQUESTERS_URI + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    public void update_not_found() throws Exception {
        when(service.update(any(), any())).thenThrow(new EmptyResultDataAccessException(1));

        String inputJson = super.mapToJson(requesters.get(BANCO_ABC_WITH_DATA_BILLING));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put(REQUESTERS_URI + "/50")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andExpect(status().isNotFound())
                .andReturn();

    }
}