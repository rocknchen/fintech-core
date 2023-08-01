package com.hthk.fintech.event.dao;

import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.model.event.EngineCriteria;
import com.hthk.fintech.model.event.EventProcessEntity;

import java.io.IOException;
import java.util.List;

public interface EngineDAO {

    List<EventProcessEntity> get(EngineCriteria criteria) throws AttributeEmptyException, IOException;

}
