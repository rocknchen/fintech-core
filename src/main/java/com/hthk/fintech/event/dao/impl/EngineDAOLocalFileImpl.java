package com.hthk.fintech.event.dao.impl;

import com.hthk.fintech.enumration.EventProcessStatusEnum;
import com.hthk.fintech.event.dao.EngineDAO;
import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.model.event.EngineCriteria;
import com.hthk.fintech.model.event.EventProcessEntity;
import com.hthk.fintech.service.basic.AbstractService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class EngineDAOLocalFileImpl extends AbstractService implements EngineDAO {

    @Override
    public List<EventProcessEntity> get(EngineCriteria criteria) throws AttributeEmptyException, IOException {

        String engineName = criteria.getName();
        List<EventProcessStatusEnum> statusList = criteria.getStatusList();

        return null;
//        String engineFilePath =
//        return getByDate(engineName, statusList);
    }

}
