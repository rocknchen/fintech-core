package com.hthk.fintech.service.basic;

import com.hthk.fintech.structure.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService {

    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    protected <T> String ymlStr(T model) {
        return JacksonUtils.toYMLPrettyTry(model);
    }

    protected <T> void logYML(T model) {
        getLogger().info("\r\n{}", JacksonUtils.toYMLPrettyTry(model));
    }

}
