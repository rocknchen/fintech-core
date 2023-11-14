package com.hthk.fintech.web.http.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.hthk.fintech.model.web.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 20:04
 */
public class HttpRequestDeserializer extends JsonDeserializer<HttpRequest<?>> {

    private final static Logger logger = LoggerFactory.getLogger(HttpRequestDeserializer.class);

    @Override
    public HttpRequest<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        logger.error("deserialize");
        return new HttpRequest();
    }

}
