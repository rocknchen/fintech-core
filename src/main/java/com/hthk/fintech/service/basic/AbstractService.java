package com.hthk.fintech.service.basic;

import com.hthk.fintech.structure.utils.JacksonUtils;

public abstract class AbstractService {

    protected <T> String ymlStr(T model) {
        return JacksonUtils.toYMLPrettyTry(model);
    }

}
