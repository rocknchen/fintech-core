package com.hthk.fintech.test.basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hthk.fintech.structure.utils.JacksonUtils;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_APP_CONTEXT_FILE;

public abstract class AbstractAppContextTest {

    protected static ApplicationContext appContext;

    protected Logger logger;

    protected LocalDateTime currentTime;

    protected ObjectMapper defaultObjMapper;

    static {
        appContext = new ClassPathXmlApplicationContext(DEFAULT_APP_CONTEXT_FILE);
    }

    @Before
    public void setUp() {

        logger = getLogger();
        currentTime = LocalDateTime.now();
        defaultObjMapper = appContext.getBean(ObjectMapper.class);
    }

    protected String toJson(Object entity) throws JsonProcessingException {
        return defaultObjMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entity);
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
