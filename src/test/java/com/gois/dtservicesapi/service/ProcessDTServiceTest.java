package com.gois.dtservicesapi.service;

import com.gois.dtservicesapi.repository.ProcessRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessDTServiceTest {

    @InjectMocks
    ProcessDTService service;

    @Mock
    ProcessRepository repository;

    @Test
    public void update() throws Exception {
        /*
        Requester process_old = new ProcessDTBuilder()
                .withId(1L).with("Banco ABC").withAcronym("ABC").build();
        when(repository.findById(any())).thenReturn(Optional.of(banco_abc_with_no_data_billing));
        Requester banco_abc_with_data_billing = new RequesterBuilder()
                .withName("Banco ABC").withAcronym("ABC").withDataBilling("Dados para faturamento").build();
        when(repository.save(any())).thenReturn(Optional.of(banco_abc_with_data_billing).get());

        Requester requester = service.update(1L, banco_abc_with_data_billing);

        assertThat(requester).isNotNull();
        assertThat(requester.getDataBilling()).isEqualTo("Dados para faturamento");
        */
    }

}