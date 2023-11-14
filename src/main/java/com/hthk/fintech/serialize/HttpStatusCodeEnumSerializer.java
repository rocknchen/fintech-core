package com.hthk.fintech.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hthk.fintech.model.web.http.HttpStatusCodeEnum;

import java.io.IOException;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 15:34
 */
public class HttpStatusCodeEnumSerializer extends StdSerializer<HttpStatusCodeEnum> {

    public HttpStatusCodeEnumSerializer() {
        this(null);
    }

    public HttpStatusCodeEnumSerializer(Class<HttpStatusCodeEnum> t) {
        super(t);
    }

    @Override
    public void serialize(HttpStatusCodeEnum value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeNumber(value.getStatusCode());
    }

}
