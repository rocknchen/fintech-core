package com.hthk.fintech.event.service;

import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.model.event.EventCriteria;
import com.hthk.fintech.model.event.IEvent;

import java.io.IOException;
import java.util.List;

public interface EventService {

    void save(IEvent event) throws AttributeEmptyException, PersistenceException;

    List<IEvent> get(EventCriteria criteria) throws AttributeEmptyException, IOException;

}
