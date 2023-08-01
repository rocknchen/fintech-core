package com.hthk.fintech.event.dao.impl;

import com.hthk.common.utils.CSVFileUtils;
import com.hthk.common.utils.LocalDateTimeUtils;
import com.hthk.common.utils.UUIDUtils;
import com.hthk.fintech.enumration.DateTimeFormatEnum;
import com.hthk.fintech.event.dao.EventDAO;
import com.hthk.fintech.event.utils.DateTimeFormatUtils;
import com.hthk.fintech.event.utils.DateTimeMaskUtils;
import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.model.event.EventCriteria;
import com.hthk.fintech.model.event.IEvent;
import com.hthk.fintech.model.event.basic.AbstractEvent;
import com.hthk.fintech.service.basic.AbstractService;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.*;

@Component
public class EventDAOLocalFileImpl extends AbstractService implements EventDAO {

    @Override
    public void save(IEvent event) throws PersistenceException, AttributeEmptyException {

        LocalDateTime eventTime = event.getTime();
        String eventFilePath = getEventFilePath(eventTime);

        String id = UUIDUtils.buildId(event, eventTime);
        setId(event, id);

        try {
            appendCSV(event, eventFilePath, eventTime);
        } catch (IOException e) {
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    /**
     * TODO
     *
     * @param criteria
     * @return
     */
    @Override
    public List<IEvent> get(EventCriteria criteria) throws AttributeEmptyException, IOException {

        LocalDateTime eventTime = criteria.getEventTime();
        List<IEvent> eventList;
        if (eventTime != null) {
            eventList = getByDate(eventTime);
        } else {
            eventList = getAll();
        }
        eventList = filter(eventList, criteria);
        return eventList;
    }

    private void setId(IEvent event, String id) throws PersistenceException {

        try {
            Method setIdMethod = AbstractEvent.class.getDeclaredMethod("setId", String.class);
            setIdMethod.invoke(event, id);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    private List<IEvent> filter(List<IEvent> eventList, EventCriteria criteria) {

        List<IEvent> newEventList = eventList;

        String domain = criteria.getDomain();
        if (StringUtils.hasText(domain)) {
            newEventList = eventList.stream().filter(t -> t.getDomain().equals(domain)).collect(Collectors.toList());
        }

        String group = criteria.getGroup().name();
        if (StringUtils.hasText(group)) {
            newEventList = eventList.stream().filter(t -> t.getGroup().equals(group)).collect(Collectors.toList());
        }

        return newEventList;
    }

    private List<IEvent> getAll() {
        return null;
    }

    private void appendCSV(IEvent event, String eventFilePath, LocalDateTime eventTime) throws IOException, AttributeEmptyException {

        boolean isNewEventFile = !new File(eventFilePath).exists();
        logStr(Boolean.valueOf(isNewEventFile).toString(), LogLevel.DEBUG, "is new event file");

        if (isNewEventFile) {
            createCSV(event, eventFilePath);
        } else {
            List<IEvent> eventList = getByDate(eventTime);
            eventList.add(0, event);
            writeCSV(eventList, eventFilePath);
        }
    }

    private List<IEvent> getByDate(LocalDateTime eventTime) throws AttributeEmptyException, IOException {

        String eventFilePath = getEventFilePath(eventTime);
        List<IEvent> eventList = CSVFileUtils.read(eventFilePath, IEvent.class);
        return eventList;
    }

    private File getEventFile(LocalDateTime eventTime) {
        return null;
    }

    private void writeCSV(List<IEvent> eventList, String eventFilePath) throws PersistenceException {
        try {
            CSVFileUtils.writeList(eventList, eventFilePath, true, IEvent.class);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    private void createCSV(IEvent event, String eventFilePath) throws PersistenceException {
        try {
            CSVFileUtils.write(event, eventFilePath, true, IEvent.class);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage(), e);
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


    private String getEventFilePath(LocalDateTime eventTime) throws AttributeEmptyException {

        logTime(eventTime, LogLevel.DEBUG, KW_EVENT_TIME);

        String monthStr = LocalDateTimeUtils.format(DateTimeFormatEnum.YYYYMM.getFormat(), eventTime);
        logStr(monthStr, LogLevel.DEBUG, KW_DATE_MONTH);

        String eventFilePathMasked = getEventFilePathMasked();
        logStr(eventFilePathMasked, LogLevel.DEBUG, "event file path masked");

        Map<String, String> dataMap = DateTimeFormatUtils.buildStrMap(eventTime);
        logStr(dataMap.toString(), LogLevel.DEBUG, null);

        String eventFilePath = DateTimeMaskUtils.unMask(eventFilePathMasked, dataMap);
        logStr(eventFilePath, LogLevel.DEBUG, "event file path");

        return eventFilePath;
    }
}
