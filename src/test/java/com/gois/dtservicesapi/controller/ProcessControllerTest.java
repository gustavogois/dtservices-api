package com.gois.dtservicesapi.controller;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.model.builders.ProcessDTBuilder;
import com.gois.dtservicesapi.model.builders.RequesterBuilder;
import com.gois.dtservicesapi.respository.ProcessRepository;
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
    ProcessRepository repository;

    private static List<Requester> requesters;
    private static final int BANCO_ABC = 0;
    private static final int BANCO_XYZ = 1;

    private static List<ProcessDT> process;
    private static final int WITH_BANCO_ABC_1 = 0;
    private static final int WITH_BANCO_ABC_2 = 1;
    private static final int WITH_BANCO_XYZ_1 = 2;
    private static final int WITH_BANCO_XYZ_2 = 3;



    @BeforeClass
    public static void setUp() throws Exception {
        Requester banco_abc = new RequesterBuilder().withId(1L).withName("Banco ABC").build();
        Requester banco_xyz = new RequesterBuilder().withId(2L).withName("Banco XYZ").build();
        requesters = Arrays.asList(banco_abc,banco_xyz);
        process = Arrays.asList(
                new ProcessDTBuilder()
                        .withExtCode("adasd123123")
                        .withDtCreation(LocalDateTime.now().minusDays(1))
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
    }

    @Test
    public void list() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROCESS_URI)
                .accept(MediaType.APPLICATION_JSON);

        when(repository.findAll()).thenReturn(process);

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
        ProcessDT process_without_ext_code = new ProcessDTBuilder().withRequester(requesters.get(BANCO_ABC)).build();

        String inputJson = super.mapToJson(process_without_ext_code);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                    .post(PROCESS_URI)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void create_400_external_code_min_size_error() throws Exception {
        ProcessDT process_with_ext_code_size_error =
                new ProcessDTBuilder().withRequester(requesters.get(BANCO_ABC))
                        .withExtCode("")
                        .build();

        String inputJson = super.mapToJson(process_with_ext_code_size_error);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post(PROCESS_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void create_400_external_code_max_size_error() throws Exception {
        ProcessDT process_with_ext_code_size_error =
                new ProcessDTBuilder().withRequester(requesters.get(BANCO_ABC))
                        .withExtCode("123456789123456789123456789")
                        .build();

        String inputJson = super.mapToJson(process_with_ext_code_size_error);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post(PROCESS_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void create() throws Exception {
        String inputJson = super.mapToJson(process.get(WITH_BANCO_ABC_1));

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