package com.hthk.fintech.web.http.exp;

import com.hthk.fintech.config.AppConfig;
import com.hthk.fintech.exception.ServiceInvalidException;
import com.hthk.fintech.exception.ServiceNotSupportedException;
import com.hthk.fintech.utils.HttpResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.PostConstruct;

import static com.hthk.fintech.config.FintechStaticData.LOG_DEFAULT;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 18:26
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class DefaultAppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAppExceptionHandler.class);

    @Autowired
    private AppConfig appConfig;

    private boolean isPrintStack;

    @PostConstruct
    public void init() {
        isPrintStack = appConfig.isControllerPrintStack();
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleNPE(RuntimeException ex) {

        logger.error("Not catch RuntimeException: {}", ex.getMessage(), ex);
        String errMsg = StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : "Not catch RuntimeException";
        ResponseEntity<Object> resp = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body("test");
        return resp;
    }

    @ExceptionHandler(ServiceInvalidException.class)
    protected ResponseEntity<Object> handleServiceInvalidException(ServiceInvalidException ex) {

        String generalErr = "Not catch ServiceInvalidException";
        logError(ex, isPrintStack, generalErr);
        String errMsg = StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : generalErr;
        ResponseEntity<Object> resp = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).
                body(HttpResponseUtils.internalError(errMsg));
        return resp;
    }

    @ExceptionHandler(ServiceNotSupportedException.class)
    protected ResponseEntity<Object> handleServiceNotSupportedException(ServiceNotSupportedException ex) {

        String generalErr = "Not catch ServiceNotSupportedException";
        logError(ex, isPrintStack, generalErr);
        String errMsg = StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : generalErr;
        ResponseEntity<Object> resp = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).
                body(HttpResponseUtils.internalError(errMsg));
        return resp;
    }

    private void logError(Exception ex, boolean isPrintStack, String msg) {
        if (isPrintStack) {
            logger.error(LOG_DEFAULT, msg, ex.getMessage(), ex);
        } else {
            logger.error(LOG_DEFAULT, msg, ex.getMessage());
        }
    }

}
