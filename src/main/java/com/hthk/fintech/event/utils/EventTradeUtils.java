package com.hthk.fintech.event.utils;

import com.hthk.fintech.enumration.EventSubTypeTradeEnum;
import com.hthk.fintech.enumration.EventTypeTradeEnum;
import com.hthk.fintech.event.utils.basic.AbstractEventUtils;
import com.hthk.fintech.model.event.EventTrade;

import java.time.LocalDateTime;

import static com.hthk.fintech.enumration.EventTypeTradeEnum.REFRESH;

public class EventTradeUtils extends AbstractEventUtils {

    public static EventTrade build(String domain, EventTypeTradeEnum type, EventSubTypeTradeEnum subType, LocalDateTime time) {
        return new EventTrade(domain, REFRESH, null, time);
    }

}
