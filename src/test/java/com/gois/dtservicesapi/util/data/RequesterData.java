package com.gois.dtservicesapi.util.data;

import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.model.builders.RequesterBuilder;

import java.util.Arrays;
import java.util.List;

public class RequesterData {

    public static List<Requester> requesters;

    public static final int BANCO_ABC_WITH_NO_DATA_BILLING = 0;
    public static final int BANCO_XYZ = 1;
    public static final int WITHOUT_ACRONYM = 2;
    public static final int BANCO_ABC_WITH_DATA_BILLING = 3;
    public static final int WITH_NAME_NULL = 4;

    public static void init() {

        Requester banco_abc = new RequesterBuilder().withId(1L).withName("Banco ABC").withAcronym("ABC").build();
        Requester banco_xyz = new RequesterBuilder().withId(2L).withName("Banco XYZ").withAcronym("XYZ").build();
        Requester without_acronym = new RequesterBuilder().withName("Banco ABC").build();
        Requester banco_abc_with_data_billing = new RequesterBuilder()
                .withName("Banco ABC").withAcronym("ABC").withDataBilling("Dados para faturamento").build();
        Requester with_name_null = new RequesterBuilder()
                .withAcronym("ABC").withDataBilling("Dados para faturamento").build();
        requesters = Arrays.asList(banco_abc, banco_xyz, without_acronym, banco_abc_with_data_billing, with_name_null);
    }

    public static List<Requester> getRequesters() {
        if (requesters == null) {
            init();
        }
        return requesters;
    }

    public static Requester get(int i) {
        if (requesters == null) {
            init();
        }
        return requesters.get(i);
    }
}
