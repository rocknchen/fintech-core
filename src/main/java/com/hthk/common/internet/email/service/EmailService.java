package com.hthk.common.internet.email.service;

import com.hthk.common.exception.ServiceException;
import com.hthk.common.model.Internet.message.email.MessageEmail;

public interface EmailService {

    void send(MessageEmail msg) throws ServiceException;

}
