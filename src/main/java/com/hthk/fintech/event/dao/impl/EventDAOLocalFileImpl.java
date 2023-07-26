package com.hthk.fintech.event.dao.impl;

import com.hthk.common.utils.LocalDateTimeUtils;
import com.hthk.fintech.event.dao.EventDAO;
import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.model.event.IEvent;
import com.hthk.fintech.service.basic.AbstractService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.hthk.fintech.config.FintechStaticData.KW_DATE_MONTH;
import static com.hthk.fintech.config.FintechStaticData.KW_EVENT_TIME;

@Component
public class EventDAOLocalFileImpl extends AbstractService implements EventDAO {

    @Override
    public void save(IEvent event) throws PersistenceException, AttributeEmptyException {

        LocalDateTime eventTime = event.getTime();
        logTime(eventTime, KW_EVENT_TIME);

        String monthStr = LocalDateTimeUtils.format("yyyyMM", eventTime);
        logStr(monthStr, KW_DATE_MONTH);
//        logYML();
//        File eventFile =
    }


}
