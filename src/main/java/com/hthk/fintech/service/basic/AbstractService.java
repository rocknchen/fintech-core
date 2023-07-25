package com.hthk.fintech.service.basic;

import com.hthk.fintech.model.event.IEvent;
import com.hthk.fintech.structure.utils.JacksonUtils;

public abstract class AbstractService {

    protected String ymlStr(IEvent event) {
        return JacksonUtils.toYMLPrettyTry(event);
    }

}
