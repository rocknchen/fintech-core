package com.hthk.fintech.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;
import com.hthk.fintech.model.web.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 20:04
 */
@JsonComponent
public class HttpRequestDeserializer<T extends IDataCriteria> extends JsonDeserializer<HttpRequest<T>> {

    private final static Logger logger = LoggerFactory.getLogger(HttpRequestDeserializer.class);

    //    @Override
//    public HttpRequest<T> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
//
//
//        return new HttpRequest();
//    }

    @Override
    public HttpRequest<T> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        logger.error("HttpRequestDeserializer");
        return new HttpRequest();
    }
}
