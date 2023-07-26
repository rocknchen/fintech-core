package com.hthk.fintech.event.dao.impl;

import com.hthk.common.utils.LocalDateTimeUtils;
import com.hthk.fintech.event.dao.EventDAO;
import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.model.event.IEvent;
import com.hthk.fintech.service.basic.AbstractService;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;

import static com.hthk.fintech.config.FintechStaticData.KW_DATE_MONTH;
import static com.hthk.fintech.config.FintechStaticData.KW_EVENT_TIME;

@Component
public class EventDAOLocalFileImpl extends AbstractService implements EventDAO {

    @Override
    public void save(IEvent event) throws PersistenceException, AttributeEmptyException {

        LocalDateTime eventTime = event.getTime();
        logTime(eventTime, LogLevel.DEBUG, KW_EVENT_TIME);

        String monthStr = LocalDateTimeUtils.format("yyyyMM", eventTime);
        logStr(monthStr, KW_DATE_MONTH);

        File eventFile = getEventFile(monthStr);
//        logYML();
//        File eventFile =
    }

    private File getEventFile(String monthStr) {

        String eventFileFolder = appConfig.getLocalFileRootFolder();
        logStr(eventFileFolder, LogLevel.DEBUG, null);

        String eventFileName = null;
        logStr(eventFileName, LogLevel.DEBUG, null);

        String eventFilePath = eventFileFolder + "/" + eventFileName;
        logStr(eventFilePath, LogLevel.DEBUG, null);

        return null;
    }

}
