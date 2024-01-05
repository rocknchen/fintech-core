package com.hthk.fintech.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hthk.common.exception.ServiceException;
import com.hthk.fintech.model.web.http.HttpStatusCodeEnum;
import com.hthk.fintech.utils.HttpStatusCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author: Rock CHEN
 * @Date: 2024/1/5 17:29
 */
public class HttpStatusCodeEnumDeserializer extends StdDeserializer<HttpStatusCodeEnum> {

    private final static Logger logger = LoggerFactory.getLogger(HttpStatusCodeEnumDeserializer.class);

    public HttpStatusCodeEnumDeserializer() {
        this(null);
    }

    public HttpStatusCodeEnumDeserializer(Class<HttpStatusCodeEnum> t) {
        super(t);
    }

    @Override
    public HttpStatusCodeEnum deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        ObjectCodec codec = parser.getCodec();
        JsonNode root = codec.readTree(parser);
        int statusCode = root.intValue();
        try {
            return HttpStatusCodeUtils.parse(statusCode);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
