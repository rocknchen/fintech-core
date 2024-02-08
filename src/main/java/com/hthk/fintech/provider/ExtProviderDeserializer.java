package com.hthk.fintech.provider;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 16:47
 */
public class ExtProviderDeserializer extends JsonDeserializer<ExtProvider> {

    @Override
    public ExtProvider deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return null;
    }

}
