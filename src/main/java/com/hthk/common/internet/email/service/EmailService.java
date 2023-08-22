package com.hthk.common.internet.email.service;

import com.hthk.common.model.Internet.message.email.MessageEmail;

public interface EmailService {

    void send(MessageEmail msg);

}
