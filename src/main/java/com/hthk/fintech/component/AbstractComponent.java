package com.hthk.fintech.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hthk.fintech.serialize.DefaultObjectMapperFactory;
import com.hthk.fintech.service.AppInfoService;
import com.hthk.fintech.service.DataQueryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 16:16
 */
public abstract class AbstractComponent {

    @Resource(name = "basicAppInfoService")
    protected AppInfoService appInfoService;

    @Autowired
    protected DataQueryService dataQueryService;

    @Autowired
    protected DefaultObjectMapperFactory mapperFactory;

    public ObjectMapper getDefaultObjectMapper() {
        return mapperFactory.getObjectMapper();
    }
}
