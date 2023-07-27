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

import static com.hthk.fintech.config.FintechStaticData.*;

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

        String rootFolder = appConfig.getLocalFileRootFolder();
        logStr(rootFolder, LogLevel.DEBUG, null);

        String eventSubFolderMasked = appConfig.getEventFolder();
        logStr(eventSubFolderMasked, LogLevel.DEBUG, null);

        String eventFileFolderMasked = rootFolder + "/" + eventSubFolderMasked;
        logStr(eventFileFolderMasked, LogLevel.DEBUG, null);

        String eventFileName = KW_EVENT_FILE_NAME;
        String eventFullPathMasked = eventFileFolderMasked + "/" + eventFileName;

        String eventFullPath = dateUnMask(eventFullPathMasked);
        logStr(eventFullPath, LogLevel.DEBUG, null);

        return null;
    }

    private String dateUnMask(String dateMaskedStr) {
        return dateMaskedStr;
    }

}
