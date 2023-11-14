package com.hthk.fintech.web.http.exp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 18:26
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class DefaultAppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAppExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleNPE(RuntimeException ex) {

        logger.error("Not catch RuntimeException: {}", ex.getMessage(), ex);
        String errMsg = StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : "Not catch RuntimeException";
        ResponseEntity<Object> resp = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body("test");
        return resp;
    }

}
