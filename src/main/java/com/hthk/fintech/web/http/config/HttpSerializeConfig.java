package com.hthk.fintech.web.http.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 15:07
 */
@Configuration
public class HttpSerializeConfig implements WebMvcConfigurer {

    @Resource(name = "httpSerializeDefaultObjectMapper")
    private ObjectMapper httpResponseMapper;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(httpResponseMapper);
        converters.add(0, messageConverter);
    }

}
