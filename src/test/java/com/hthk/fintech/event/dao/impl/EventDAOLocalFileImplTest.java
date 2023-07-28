package com.hthk.fintech.event.dao.impl;

import com.hthk.fintech.event.dao.EventDAO;
import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.model.event.EventTrade;
import com.hthk.fintech.test.EventTradeTest;
import org.junit.Test;

import static com.hthk.fintech.enumration.EventGroupEnum.TRADE;
import static com.hthk.fintech.enumration.EventTypeTradeEnum.REFRESH;

public class EventDAOLocalFileImplTest extends EventTradeTest {

    final String DOMAIN = "CALYPSO";

    EventDAO dao;

    @Override
    public void setUp() {
        super.setUp();
        dao = appContext.getBean(EventDAO.class);
    }

    @Test
    public void testSaveEvent_TRADE_REFRESH_CALYPSO() throws PersistenceException, AttributeEmptyException {

        event = EventTrade.newInstance(DOMAIN, REFRESH, null, currentTime);
        dao.save(event);
    }

    //    @Test
    public void testSaveEvent_TRADE_REFRESH_CALYPSO2() {

        event = EventTrade.newInstance(DOMAIN, REFRESH, null, currentTime);
        logYML(event);
//        dao.save(event);
    }

}