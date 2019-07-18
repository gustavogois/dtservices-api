package com.gois.dtservicesapi.service;

import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.model.builders.RequesterBuilder;
import com.gois.dtservicesapi.repository.RequesterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequesterServiceTest {

    @InjectMocks
    RequesterService service;

    @Mock
    RequesterRepository repository;

    @Test
    public void update() throws Exception {
        Requester banco_abc_with_no_data_billing = new RequesterBuilder()
                .withId(1L).withName("Banco ABC").withAcronym("ABC").build();
        when(repository.findById(any())).thenReturn(Optional.of(banco_abc_with_no_data_billing));
        Requester banco_abc_with_data_billing = new RequesterBuilder()
                .withName("Banco ABC").withAcronym("ABC").withDataBilling("Dados para faturamento").build();
        when(repository.save(any())).thenReturn(Optional.of(banco_abc_with_data_billing).get());

        Requester requester = service.update(1L, banco_abc_with_data_billing);

        assertThat(requester).isNotNull();
        assertThat(requester.getDataBilling()).isEqualTo("Dados para faturamento");
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void update_not_found() throws Exception {
        when(repository.findById(any())).thenThrow(new EmptyResultDataAccessException(1));

        Requester requester = service.update(1L, new RequesterBuilder().build());

    }

}