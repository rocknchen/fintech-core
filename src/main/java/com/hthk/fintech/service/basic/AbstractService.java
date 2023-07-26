package com.hthk.fintech.service.basic;

import com.hthk.fintech.structure.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_DATE_TIME_FORMAT;

public abstract class AbstractService {

    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    protected <T> String ymlStr(T model) {
        return JacksonUtils.toYMLPrettyTry(model);
    }

    protected <T> void logYML(T model) {
        getLogger().info("\r\n{}", JacksonUtils.toYMLPrettyTry(model));
    }

    protected <T> void logStr(T model) {
        getLogger().info("{}", model);
    }

    protected void logTime(LocalDateTime time) {
        logTime(time, null);
    }

    protected void logTime(LocalDateTime time, String msg) {
        String timeMsg = Optional.ofNullable(time).map(t -> t.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT))).orElse(null);
        String logMsg = StringUtils.hasText(msg) ? msg + ": {}" : "{}";
        getLogger().info(logMsg, timeMsg);
    }

}
