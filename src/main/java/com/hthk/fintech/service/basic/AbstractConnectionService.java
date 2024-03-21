package com.hthk.fintech.service.basic;

import com.hthk.fintech.exception.ServiceInternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/21 18:18
 */
public abstract class AbstractConnectionService extends AbstractService {

    private final static Logger logger = LoggerFactory.getLogger(AbstractConnectionService.class);

    protected void log(String serverIP, boolean loginResult) {
        String result = "connect " + serverIP + " " + (loginResult ? "success" : "failed");
        if (loginResult) {
            logger.info(result);
        } else {
            logger.error(result);
        }
    }

    protected void exp(boolean isSuccess) throws ServiceInternalException {
        if (!isSuccess) {
            throw new ServiceInternalException("connect failed");
        }
    }
}
