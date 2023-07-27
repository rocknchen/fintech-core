package com.hthk.fintech.event.dao.impl;

import com.hthk.common.utils.LocalDateTimeUtils;
import com.hthk.fintech.enumration.DateTimeFormatEnum;
import com.hthk.fintech.event.dao.EventDAO;
import com.hthk.fintech.event.utils.DateTimeFormatUtils;
import com.hthk.fintech.event.utils.DateTimeMaskUtils;
import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.model.event.IEvent;
import com.hthk.fintech.service.basic.AbstractService;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;

import static com.hthk.fintech.config.FintechStaticData.*;

@Component
public class EventDAOLocalFileImpl extends AbstractService implements EventDAO {

    @Override
    public void save(IEvent event) throws PersistenceException, AttributeEmptyException {

        logYML(event, LogLevel.INFO, KW_SAVE_EVENT);

        LocalDateTime eventTime = event.getTime();
        logTime(eventTime, LogLevel.DEBUG, KW_EVENT_TIME);

        String monthStr = LocalDateTimeUtils.format(DateTimeFormatEnum.YYYYMM.getFormat(), eventTime);
        logStr(monthStr, LogLevel.DEBUG, KW_DATE_MONTH);

        String eventFilePathMasked = getEventFilePathMasked();
        logStr(eventFilePathMasked, LogLevel.DEBUG, "event file path masked");

        Map<String, String> dataMap = DateTimeFormatUtils.buildStrMap(eventTime);
        logStr(dataMap.toString(), LogLevel.DEBUG, null);

        String eventFilePath = DateTimeMaskUtils.unMask(eventFilePathMasked, dataMap);
        logStr(eventFilePath, LogLevel.DEBUG, "event file path");

        File eventFile = new File(eventFilePath);
        File parentPath = eventFile.getParentFile();

        buildFolder(parentPath);
        appendCSV(event, eventFile);
    }

    private void appendCSV(IEvent event, File eventFile) {

        boolean isNewEventFile = !eventFile.exists();
        logStr(Boolean.valueOf(isNewEventFile).toString(), LogLevel.DEBUG, "is new event file");
    }

    private void buildFolder(File parentPath) {
        if (!parentPath.exists()) {
            parentPath.mkdirs();
        }
    }

    private String getEventFilePathMasked() {

        String rootFolder = appConfig.getLocalFileRootFolder();
        logStr(rootFolder, LogLevel.DEBUG, null);

        String eventSubFolderMasked = appConfig.getEventFolder();
        logStr(eventSubFolderMasked, LogLevel.DEBUG, null);

        String eventFileFolderMasked = rootFolder + "/" + eventSubFolderMasked;
        logStr(eventFileFolderMasked, LogLevel.DEBUG, null);

        String eventFileName = KW_EVENT_FILE_NAME;
        String eventFullPathMasked = eventFileFolderMasked + "/" + eventFileName;
        return eventFullPathMasked;
    }

}
