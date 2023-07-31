package com.hthk.fintech.event.dao.impl;

import com.hthk.common.utils.UUIDUtils;
import com.hthk.fintech.enumration.EventTypeMarketDataEnum;
import com.hthk.fintech.event.dao.EventDAO;
import com.hthk.fintech.event.utils.EventUtils;
import com.hthk.fintech.exception.AttributeEmptyException;
import com.hthk.fintech.exception.PersistenceException;
import com.hthk.fintech.model.event.EventMarketData;
import com.hthk.fintech.model.event.EventTrade;
import com.hthk.fintech.test.EventTradeTest;
import org.junit.Test;

import java.util.UUID;

import static com.hthk.fintech.enumration.EventSubTypeMarketDataEnum.READY;
import static com.hthk.fintech.enumration.EventTypeMarketDataEnum.VOL_SURFACE;
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
    public void testSaveEvent_VOL_COMMODITY_0900HKT_READY_CALYPSO() throws AttributeEmptyException, PersistenceException {

        event = EventMarketData.newInstance(DOMAIN, VOL_SURFACE, READY, currentTime);
        dao.save(event);
    }

}