package com.hthk.fintech.event.service;

import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.model.event.EngineCriteria;
import com.hthk.fintech.model.event.EventProcessEntity;

import java.io.IOException;
import java.util.List;

public interface EngineService {

    List<EventProcessEntity> getEntity(EngineCriteria criteria) throws AttributeEmptyException, IOException;

    List<EventProcessEntity> upsert(List<EventProcessEntity> entityList);

}
