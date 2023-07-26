package com.hthk.fintech.event.dao.impl;

import com.hthk.fintech.test.basic.AbstractTest;
import org.junit.Before;
import org.junit.Test;

import static com.hthk.fintech.config.FintechStaticData.LOG_FORMAT_ONE_MODEL;
import static org.junit.Assert.*;

public class EventDAOLocalFileImplTest extends AbstractTest {

    @Test
    public void testSaveEvent_TRADE_REFRESH_CALYPSO() {

        log(LOG_FORMAT_ONE_MODEL, "test", "1");
    }

}