package com.hthk.fintech.event.service;

import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.model.event.IEvent;

public interface EventService {

    void save(IEvent event) throws AttributeEmptyException, PersistenceException;

}
