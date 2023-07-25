package com.hthk.fintech.event.utils;

import com.hthk.fintech.enumration.EventTypeTradeEnum;
import com.hthk.fintech.event.utils.basic.AbstractEventUtils;
import com.hthk.fintech.model.event.EventTrade;

import java.time.LocalDateTime;

public class EventTradeUtils extends AbstractEventUtils {

    public static EventTrade build(String domain, EventTypeTradeEnum eventTypeTrade, LocalDateTime eventTime) {
        return new EventTrade(domain, eventTypeTrade, eventTime);
    }

}
