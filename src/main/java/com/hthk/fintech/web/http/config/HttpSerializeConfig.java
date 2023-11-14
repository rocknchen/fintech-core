package com.hthk.fintech.web.http.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hthk.fintech.serialize.HttpSerializeDefaultObjectMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 15:07
 */
@Configuration
public class HttpSerializeConfig implements WebMvcConfigurer {

    @Autowired
    private HttpSerializeDefaultObjectMapperFactory mapperFactory;

    private ObjectMapper httpResponseMapper;

    @PostConstruct
    public void init() {
        httpResponseMapper = mapperFactory.getObjectMapper();
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(httpResponseMapper);
        converters.add(0, messageConverter);
    }

}
