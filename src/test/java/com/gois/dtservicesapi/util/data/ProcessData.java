package com.gois.dtservicesapi.util.data;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.model.builders.ProcessDTBuilder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ProcessData {

    private static List<ProcessDT> processes;

    public static final int WITH_BANCO_ABC_1 = 0;
    public static final int WITHOUT_EXTERNAL_CODE = 1;
    public static final int PROCESS_WITH_EXT_CODE_MIN_SIZE_ERROR = 2;
    public static final int PROCESS_WITH_EXT_CODE_MAX_SIZE_ERROR = 3;

    public static List<ProcessDT> getProcesses() {
        if (processes == null) {
            init();
        }
        return processes;
    }

    public static ProcessDT get(int i) {
        if (processes == null) {
            init();
        }
        return processes.get(i);
    }

    public static String getProcessWithRequesterNotFoundJSON() {
        return "{\n" +
                "\t\"externalCode\":\"ADWER234242\",\n" +
                "\t\"internalCode\":\"WHT0001\",\n" +
                "\t\"dtCreation\":[2019,7,9,20,12],\n" +
                "\t\"requester\":{\n" +
                "\t\t\"id\":1000\n" +
                "\t}\n" +
                "}";
    }

    private static void init() {
        processes = Arrays.asList(
                new ProcessDTBuilder()
                        .withExtCode("adasd123123")
                        .withDtCreation(LocalDate.now().minusDays(1))
                        .withRequester(RequesterData.get(RequesterData.BANCO_ABC_WITH_DATA_BILLING)).build(),
                new ProcessDTBuilder()
                        .withDtCreation(LocalDate.now().minusDays(2))
                        .withRequester(RequesterData.get(RequesterData.BANCO_ABC_WITH_DATA_BILLING)).build(),
                new ProcessDTBuilder().withRequester(RequesterData.get(RequesterData.BANCO_ABC_WITH_DATA_BILLING))
                        .withExtCode("")
                        .build(),
                new ProcessDTBuilder().withRequester(RequesterData.get(RequesterData.BANCO_ABC_WITH_DATA_BILLING))
                        .withExtCode("123456789123456789123456789")
                        .build()
        );
    }


}
