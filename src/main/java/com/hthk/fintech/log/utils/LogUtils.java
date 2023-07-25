package com.hthk.fintech.log.utils;

import com.hthk.fintech.model.event.IEvent;
import com.hthk.fintech.service.impl.EventServiceDefaultImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

    public static void logYML(Logger logger, String kwSaveEvent, IEvent event) {
        logger.info(kwSaveEvent + " {}", event);
    }
}
