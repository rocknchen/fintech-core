package com.hthk.fintech.service.impl;

import com.hthk.fintech.log.utils.LogUtils;
import com.hthk.fintech.model.event.IEvent;
import com.hthk.fintech.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.hthk.fintech.config.FintechStaticData.KW_SAVE_EVENT;

@Service
public class EventServiceDefaultImpl implements EventService {

    private final static Logger logger = LoggerFactory.getLogger(EventServiceDefaultImpl.class);

    @Override
    public void save(IEvent event) {

        LogUtils.logYML(logger, KW_SAVE_EVENT, event);

    }

}
