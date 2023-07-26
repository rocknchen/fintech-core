package com.hthk.fintech.event.dao;

import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.model.event.IEvent;

public interface EventDAO {

    void save(IEvent event) throws PersistenceException;

}
