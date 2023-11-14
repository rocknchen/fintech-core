package com.hthk.fintech.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hthk.fintech.service.AppInfoService;

import javax.annotation.Resource;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 16:16
 */
public abstract class AbstractComponent {

    @Resource(name = "basicAppInfoService")
    protected AppInfoService appInfoService;

    @Resource(name = "defaultObjectMapper")
    protected ObjectMapper defaultObjectMapper;

}
