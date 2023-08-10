package com.hthk.fintech.utils;

import com.hthk.fintech.enumration.BasicInstrumentGroupEnum;
import com.hthk.fintech.model.instrument.BasicInstrument;
import com.hthk.fintech.model.instrument.IInstrument;

public class BasicInstrumentUtils {

    public static IInstrument build(BasicInstrumentGroupEnum instGroup, String name) {

        BasicInstrument basicInst = new BasicInstrument();
        basicInst.setBasicInstrumentGroup(instGroup);
        basicInst.setName(name);
        return basicInst;
    }

    public static IInstrument buildUnknownGroup(String name) {
        return build(BasicInstrumentGroupEnum.UNKNOWN, name);
    }

}
