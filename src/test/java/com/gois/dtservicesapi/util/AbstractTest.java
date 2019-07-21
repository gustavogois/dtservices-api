package com.gois.dtservicesapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public abstract class AbstractTest {

    @Autowired
    private ObjectMapper objectMapper;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {

        return objectMapper.readValue(json, clazz);
    }
    protected String createURLWithPort(String uri, int port) {
        return "http://localhost:" + port + uri;
    }
}
