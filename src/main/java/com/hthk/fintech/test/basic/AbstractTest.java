package com.hthk.fintech.test.basic;

import com.hthk.fintech.structure.utils.JacksonUtils;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public abstract class AbstractTest {

    protected Logger logger;

    protected LocalDateTime currentTime;

    @Before
    public void setUp() {

        logger = getLogger();
        currentTime = LocalDateTime.now();
    }

    protected void log(String logFormat, String msg1, String msg2) {
        logger.info(logFormat, msg1, msg2);
    }

    private Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    protected <T> void logYML(T model) {
        logger.info("\r\n{}", JacksonUtils.toYMLPrettyTry(model));
    }

}
