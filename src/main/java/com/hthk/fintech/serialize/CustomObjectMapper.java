package com.hthk.fintech.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 16:35
 */
@Component
public class CustomObjectMapper {

    @Autowired
    private ObjectMapper objectMapper;

    public <T, R> R readValue(T entity, Class<R> clz) throws JsonProcessingException {

        String entityStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entity);
        return objectMapper.readValue(entityStr, clz);
    }

}
