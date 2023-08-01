package com.hthk.fintech.event.service.impl;

import com.hthk.fintech.event.dao.EngineDAO;
import com.hthk.fintech.event.service.EngineService;
import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.model.event.EngineCriteria;
import com.hthk.fintech.model.event.EventProcessEntity;
import com.hthk.fintech.service.basic.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EngineServiceDefaultImpl

        extends AbstractService implements EngineService {

    @Autowired
    private EngineDAO dao;

    @Override
    public List<EventProcessEntity> getEntity(EngineCriteria criteria) throws AttributeEmptyException, IOException {
        return dao.get(criteria);
    }

    @Override
    public List<EventProcessEntity> upsert(List<EventProcessEntity> entityList) {
        return null;
    }

}
