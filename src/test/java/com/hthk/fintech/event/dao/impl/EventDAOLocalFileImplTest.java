package com.hthk.fintech.event.dao.impl;

import com.hthk.fintech.model.event.EventTrade;
import com.hthk.fintech.model.event.IEvent;
import com.hthk.fintech.test.EventTradeTest;
import com.hthk.fintech.test.basic.AbstractEventTest;
import com.hthk.fintech.test.basic.AbstractTest;
import org.junit.Before;
import org.junit.Test;

import static com.hthk.fintech.config.FintechStaticData.LOG_FORMAT_ONE_MODEL;
import static org.junit.Assert.*;

public class EventDAOLocalFileImplTest extends EventTradeTest {

    @Override
    public void setUp() {

        super.setUp();
        event = new EventTrade();
    }

    @Test
    public void testSaveEvent_TRADE_REFRESH_CALYPSO() {

//        event.set
        logYML(event);
    }

}