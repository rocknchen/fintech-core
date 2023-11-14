package com.hthk.fintech.serialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.hthk.fintech.model.web.http.HttpStatusCodeEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_DATE_FORMAT;
import static com.hthk.fintech.config.FintechStaticData.DEFAULT_DATE_TIME_FORMAT;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 15:27
 */
@Component("httpSerializeDefaultObjectMapper")
public class HttpSerializeDefaultObjectMapper extends ObjectMapper {

    public HttpSerializeDefaultObjectMapper() {

        super();
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SimpleModule simpleModule = new SimpleModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(HttpStatusCodeEnum.class, new HttpStatusCodeEnumSerializer());

        this.registerModule(simpleModule);

    }
}
