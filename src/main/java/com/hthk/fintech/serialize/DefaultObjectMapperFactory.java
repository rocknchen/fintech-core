package com.hthk.fintech.serialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.hthk.fintech.model.common.JsonSerializeConfig;
import com.hthk.fintech.model.data.datacenter.query.DataCriteria;
import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;
import com.hthk.fintech.model.web.http.HttpRequestGetParams;
import com.hthk.fintech.model.web.http.HttpRequestGetParamsConfig;
import com.hthk.fintech.model.web.http.HttpServiceRequest;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.*;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 19:00
 */
@Component
public class DefaultObjectMapperFactory {

    private final static Logger logger = LoggerFactory.getLogger(DefaultObjectMapperFactory.class);

    private ObjectMapper objectMapper;

    private static Map<Class, Class> serializerConfigMap;

    static {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(DEFAULT_PACKAGE));
        Set<Class<?>> supertypeSet = reflections.getTypesAnnotatedWith(JsonSerializeConfig.class);
        List<Class<?>> dataCriteriaList = supertypeSet.stream().filter(t -> t.getAnnotation(JsonSerializeConfig.class) != null)
                .collect(Collectors.toList());
        serializerConfigMap = dataCriteriaList.stream().collect(Collectors.toMap(t -> {
            JsonSerializeConfig dataCriteria = t.getAnnotation(JsonSerializeConfig.class);
            return dataCriteria.target();
        }, Function.identity()));
    }

    public DefaultObjectMapperFactory() {

        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SimpleModule simpleModule = new SimpleModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));

        setMinIn(objectMapper);

        objectMapper.registerModule(simpleModule);

    }

    private void setMinIn(ObjectMapper objectMapper) {

        serializerConfigMap.forEach((k, v) -> objectMapper.addMixIn(k, v));
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
