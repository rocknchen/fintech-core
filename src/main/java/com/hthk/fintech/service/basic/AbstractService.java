package com.hthk.fintech.service.basic;

import com.hthk.fintech.config.AppConfig;
import com.hthk.fintech.structure.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_DATE_TIME_FORMAT;

public abstract class AbstractService {

    @Autowired
    protected AppConfig appConfig;

    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    protected <T> String ymlStr(T model) {
        return JacksonUtils.toYMLPrettyTry(model);
    }

    protected <T> void logYML(T model) {
        logYML(model, LogLevel.INFO, null);
    }

    protected <T> void logYML(T model, String msg) {
        logStr(model, LogLevel.INFO, msg);
    }

    protected <T> void logYML(T model, LogLevel level, String msg) {
        String logMsg = StringUtils.hasText(msg) ? msg + "\r\n{}" : "{}";
        String yml = JacksonUtils.toYMLPrettyTry(model);
        switch (level) {
            case DEBUG:
                getLogger().debug(logMsg, yml);
                break;
            default:
                getLogger().info(logMsg, yml);
                break;
        }
    }

    protected <T> void logStr(T model) {
        logStr(model, null);
    }

    protected <T> void logStr(T model, String msg) {
        logStr(model, LogLevel.INFO, msg);
    }

    protected <T> void logStr(T model, LogLevel level, String msg) {
        String logMsg = StringUtils.hasText(msg) ? msg + ": {}" : "{}";
        switch (level) {
            case DEBUG:
                getLogger().debug(logMsg, model);
                break;
            default:
                getLogger().info(logMsg, model);
                break;
        }
    }

    protected void logTime(LocalDateTime time) {
        logTime(time, LogLevel.INFO, null);
    }

    protected void logTime(LocalDateTime time, String msg) {
        logTime(time, LogLevel.INFO, msg);
    }

    protected void logTime(LocalDateTime time, LogLevel level, String msg) {
        String timeMsg = Optional.ofNullable(time).map(t -> t.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT))).orElse(null);
        String logMsg = StringUtils.hasText(msg) ? msg + ": {}" : "{}";
        switch (level) {
            case DEBUG:
                getLogger().debug(logMsg, timeMsg);
                break;
            default:
                getLogger().info(logMsg, timeMsg);
                break;
        }
    }

}
