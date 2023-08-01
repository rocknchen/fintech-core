package com.hthk.fintech.event.service.impl;

import com.hthk.fintech.event.dao.EventDAO;
import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.log.utils.LogUtils;
import com.hthk.fintech.model.event.EventCriteria;
import com.hthk.fintech.model.event.IEvent;
import com.hthk.fintech.event.service.EventService;
import com.hthk.fintech.service.basic.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.hthk.fintech.config.FintechStaticData.KW_SAVE_EVENT;

@Service
public class EventServiceDefaultImpl extends AbstractService implements EventService {

    private final static Logger logger = LoggerFactory.getLogger(EventServiceDefaultImpl.class);

    @Autowired
    private EventDAO dao;

    @Override
    public void save(IEvent event) throws AttributeEmptyException, PersistenceException {

        logger.info(LogUtils.wrapStr(KW_SAVE_EVENT), ymlStr(event));
        dao.save(event);
    }

    @Override
    public List<IEvent> get(EventCriteria criteria) throws AttributeEmptyException, IOException {

        logYML(criteria);
        return dao.get(criteria);
    }

}
