package com.hthk.common.utils;

import com.hthk.fintech.model.event.EventProcessEntity;
import com.hthk.fintech.model.event.IEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_DATE_TIME_FORMAT;
import static com.hthk.fintech.config.FintechStaticData.DEFAULT_NA_STRING;

public class UUIDUtils {

    public static String buildId(IEvent event, LocalDateTime eventTime) {

        if (event == null) {
            return null;
        }
        String id = event.getDomain() + "-"
                + event.getGroup() + "-"
                + event.getType() + "-"
                + Optional.ofNullable(event.getSubType()).map(t -> t.name()).orElse(DEFAULT_NA_STRING) + "-"
                + eventTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
        return UUID.nameUUIDFromBytes(id.getBytes()).toString();
    }

    public static String buildId(EventProcessEntity entity, LocalDateTime eventTime) {

        if (entity == null) {
            return null;
        }
        String id = entity.getEventId() + "-"
                + eventTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
        return UUID.nameUUIDFromBytes(id.getBytes()).toString();
    }

}
