package com.hthk.fintech.event.dao;

import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.model.event.EventCriteria;
import com.hthk.fintech.model.event.IEvent;

import java.util.List;

public interface EventDAO {

    void save(IEvent event) throws PersistenceException, AttributeEmptyException;

    List<IEvent> get(EventCriteria criteria) throws AttributeEmptyException;

}
