package com.hthk.fintech.event.dao.impl;

import com.hthk.common.utils.LocalDateTimeUtils;
import com.hthk.fintech.event.dao.EventDAO;
import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.model.event.IEvent;
import com.hthk.fintech.service.basic.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventDAOLocalFileImpl extends AbstractService implements EventDAO {

    private final static Logger logger = LoggerFactory.getLogger(EventDAOLocalFileImpl.class);

    @Override
    public void save(IEvent event) throws PersistenceException {

        LocalDateTime time = event.getTime();
        logYML(event);
        String monthStr = LocalDateTimeUtils.format("yyyyMM", time);
//        File eventFile =
    }

}
