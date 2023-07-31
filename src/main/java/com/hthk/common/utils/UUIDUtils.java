package com.hthk.common.utils;

import com.hthk.fintech.model.event.IEvent;

import java.util.Optional;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_NA_STRING;

public class UUIDUtils {

    public static String buildId(IEvent event) {

        if (event == null) {
            return null;
        }
        return event.getDomain() + "-" + event.getGroup() + "-" + event.getType() + "-" + Optional.ofNullable(event.getSubType()).map(t -> t.name()).orElse(DEFAULT_NA_STRING);
    }

}
