package com.hthk.fintech.event.dao.impl;

import com.hthk.common.utils.CSVFileUtils;
import com.hthk.common.utils.LocalDateTimeUtils;
import com.hthk.common.utils.UUIDUtils;
import com.hthk.fintech.enumration.DateTimeFormatEnum;
import com.hthk.fintech.enumration.EventProcessStatusEnum;
import com.hthk.fintech.event.dao.EngineDAO;
import com.hthk.fintech.event.utils.DateTimeFormatUtils;
import com.hthk.fintech.event.utils.DateTimeMaskUtils;
import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.model.event.EngineCriteria;
import com.hthk.fintech.model.event.EventProcessEntity;
import com.hthk.fintech.service.basic.AbstractService;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.*;

@Component
public class EngineDAOLocalFileImpl extends AbstractService implements EngineDAO {

    @Override
    public List<EventProcessEntity> get(EngineCriteria criteria) throws AttributeEmptyException, IOException {

        if (true) {
            return null;
        }

        LocalDateTime currentDate = LocalDateTime.now();
        List<EventProcessEntity> all = getByDate(currentDate);
        if (all == null) {
            all = new ArrayList<>();
        }

        String eventId = criteria.getEventId();
        return all.stream().filter(t -> t.getEventId().equals(eventId)).collect(Collectors.toList());
    }

    @Override
    public List<EventProcessEntity> upsert(List<EventProcessEntity> entityList) throws AttributeEmptyException, IOException {

        LocalDateTime engineUpdateTime = entityList.get(0).getUpdatedDateTime();
        String engineFilePath = getEventFilePath(engineUpdateTime);

        return entityList.stream().map(t -> {
            try {
                return upsert(t, engineFilePath, engineUpdateTime);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private EventProcessEntity upsert(EventProcessEntity entity, String engineFilePath, LocalDateTime engineUpdateTime) throws IOException, AttributeEmptyException {

        EngineCriteria criteria = new EngineCriteria();
        criteria.setEventId(entity.getEventId());
        List<EventProcessEntity> entityPList = get(criteria);

        boolean isExists = !CollectionUtils.isEmpty(entityPList);

        if (isExists) {
            return entity;
        } else {
            String id = UUIDUtils.buildId(entity, engineUpdateTime);
            entity.setId(id);
            return appendCSV(entity, engineFilePath, engineUpdateTime);
        }
    }

    private EventProcessEntity appendCSV(EventProcessEntity entity, String engineFilePath, LocalDateTime engineUpdateTime) throws AttributeEmptyException, IOException {

        boolean isNewEventFile = !new File(engineFilePath).exists();
        logStr(Boolean.valueOf(isNewEventFile).toString(), LogLevel.DEBUG, "is new event file");

        if (isNewEventFile) {
            createCSV(entity, engineFilePath);
            return entity;
        } else {
            List<EventProcessEntity> eventList = getByDate(engineUpdateTime);
            eventList.add(0, entity);
            writeCSV(eventList, engineFilePath);
            return entity;
        }
    }

    private List<EventProcessEntity> getByDate(LocalDateTime eventTime) throws AttributeEmptyException, IOException {

        String eventFilePath = getEventFilePath(eventTime);
        List<EventProcessEntity> eventList = CSVFileUtils.read(eventFilePath, EventProcessEntity.class);
        return eventList;
    }

    private String getEngineFilePathMasked() {

        String rootFolder = appConfig.getLocalFileRootFolder();
        logStr(rootFolder, LogLevel.DEBUG, null);

        String eventSubFolderMasked = appConfig.getEventFolder();
        logStr(eventSubFolderMasked, LogLevel.DEBUG, null);

        String eventFileFolderMasked = rootFolder + "/" + eventSubFolderMasked;
        logStr(eventFileFolderMasked, LogLevel.DEBUG, null);

        String eventFileName = KW_ENGINE_FILE_NAME;
        String eventFullPathMasked = eventFileFolderMasked + "/" + eventFileName;
        return eventFullPathMasked;
    }


    private String getEventFilePath(LocalDateTime eventTime) throws AttributeEmptyException {

        logTime(eventTime, LogLevel.DEBUG, KW_EVENT_TIME);

        String monthStr = LocalDateTimeUtils.format(DateTimeFormatEnum.YYYYMM.getFormat(), eventTime);
        logStr(monthStr, LogLevel.DEBUG, KW_DATE_MONTH);

        String eventFilePathMasked = getEngineFilePathMasked();
        logStr(eventFilePathMasked, LogLevel.DEBUG, "event file path masked");

        Map<String, String> dataMap = DateTimeFormatUtils.buildStrMap(eventTime);
        logStr(dataMap.toString(), LogLevel.DEBUG, null);

        String eventFilePath = DateTimeMaskUtils.unMask(eventFilePathMasked, dataMap);
        logStr(eventFilePath, LogLevel.DEBUG, "event file path");

        return eventFilePath;
    }

    private void writeCSV(List<EventProcessEntity> eventList, String eventFilePath) throws PersistenceException {
        try {
            CSVFileUtils.writeList(eventList, eventFilePath, true, EventProcessEntity.class);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    private void createCSV(EventProcessEntity event, String eventFilePath) throws PersistenceException {
        try {
            CSVFileUtils.write(event, eventFilePath, true, EventProcessEntity.class);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage(), e);
        }
    }

}
