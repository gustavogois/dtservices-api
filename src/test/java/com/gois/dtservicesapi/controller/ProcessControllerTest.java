package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.model.builders.ProcessDTBuilder;
import com.gois.dtservicesapi.model.builders.RequesterBuilder;
import com.gois.dtservicesapi.service.ProcessDTService;
import com.gois.dtservicesapi.util.AbstractTest;
import org.junit.BeforeClass;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProcessController.class)
public class ProcessControllerTest extends AbstractTest {

    public static final String PROCESS_URI = "/process";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProcessDTService service;

    private static List<Requester> requesters;
    private static final int BANCO_ABC = 0;
    private static final int BANCO_XYZ = 1;

    private static List<ProcessDT> process;
    private static final int WITH_BANCO_ABC_1 = 0;
    private static final int WITHOUT_EXTERNAL_CODE = 1;
    private static final int PROCESS_WITH_EXT_CODE_MIN_SIZE_ERROR = 2;
    private static final int PROCESS_WITH_EXT_CODE_MAX_SIZE_ERROR = 3;

    @BeforeClass
    public static void setUp() throws Exception {
        Requester banco_abc = new RequesterBuilder().withId(1L).withName("Banco ABC").withAcronym("ABC").build();
        Requester banco_xyz = new RequesterBuilder().withId(2L).withName("Banco XYZ").withAcronym("XYZ").build();
        requesters = Arrays.asList(banco_abc,banco_xyz);
        process = Arrays.asList(
                new ProcessDTBuilder()
                        .withExtCode("adasd123123")
                        .withDtCreation(LocalDateTime.now().minusDays(1))
                        .withRequester(banco_abc).build(),
                new ProcessDTBuilder()
                        .withDtCreation(LocalDateTime.now().minusDays(2))
                        .withRequester(banco_abc).build(),
                new ProcessDTBuilder().withRequester(requesters.get(BANCO_ABC))
                        .withExtCode("")
                        .build(),
                new ProcessDTBuilder().withRequester(requesters.get(BANCO_ABC))
                        .withExtCode("123456789123456789123456789")
                        .build()
        );
    }

    @Test
    public void list() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROCESS_URI)
                .accept(MediaType.APPLICATION_JSON);

        when(service.findAll()).thenReturn(process);

        MvcResult mvcResult = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].externalCode", is(process.get(WITH_BANCO_ABC_1)
                                                                                .getExternalCode())))
                .andReturn();

    }

    @Test
    public void create_400_external_code() throws Exception {

        String inputJson = super.mapToJson(process.get(WITHOUT_EXTERNAL_CODE));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                    .post(PROCESS_URI)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void create_400_external_code_min_size_error() throws Exception {

        String inputJson = super.mapToJson(process.get(PROCESS_WITH_EXT_CODE_MIN_SIZE_ERROR));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post(PROCESS_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void create_400_external_code_max_size_error() throws Exception {

        String inputJson = super.mapToJson(process.get(PROCESS_WITH_EXT_CODE_MAX_SIZE_ERROR));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post(PROCESS_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void create() throws Exception {
        ProcessDT processToBeSaved = process.get(WITH_BANCO_ABC_1);
        String inputJson = super.mapToJson(processToBeSaved);
        when(service.save(processToBeSaved)).thenReturn(processToBeSaved);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post(PROCESS_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andExpect(status().isCreated())
                .andReturn();

        ProcessDT process = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ProcessDT.class);
        assertThat(process).isNotNull();
        assertThat(process.getRequester().getAcronym()).isEqualTo("ABC");
    }

}