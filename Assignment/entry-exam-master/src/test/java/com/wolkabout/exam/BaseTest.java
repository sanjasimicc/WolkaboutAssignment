package com.wolkabout.exam;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false, print = MockMvcPrint.LOG_DEBUG)
public abstract class BaseTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired(required = false)
    protected MockMvc mockMvc;

    protected String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to serialize object", e);
        }
    }
}
