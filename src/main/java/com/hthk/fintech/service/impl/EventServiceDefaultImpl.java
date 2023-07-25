package com.hthk.fintech.service.impl;

import com.hthk.fintech.model.event.IEvent;
import com.hthk.fintech.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EventServiceDefaultImpl implements EventService {

    private final static Logger logger = LoggerFactory.getLogger(EventServiceDefaultImpl.class);

    @Override
    public void save(IEvent event) {

        logger.info("save event: {}", event);

    }

}
