package com.hthk.fintech.service;

import com.hthk.common.exception.ServiceException;

public interface ScheduledTaskManager {

    default void start(String reqStr) throws ServiceException {
    }

    /**
     * TODO
     */
    default void startByFiles(String... reqFiles) throws ServiceException {
    }

}
