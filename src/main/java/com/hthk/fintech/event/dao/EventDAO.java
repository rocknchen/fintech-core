package com.hthk.fintech.event.dao;

import com.hthk.fintech.model.event.IEvent;

public interface EventDAO {

    void save(IEvent event);

}
