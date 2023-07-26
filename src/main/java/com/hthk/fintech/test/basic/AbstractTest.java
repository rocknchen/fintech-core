package com.hthk.fintech.test.basic;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTest {

    protected Logger logger;

    @Before
    public void setUp() {
        logger = getLogger();
    }

    protected void log(String logFormat, String msg1, String msg2) {
        logger.info(logFormat, msg1, msg2);
    }

    private Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

}
