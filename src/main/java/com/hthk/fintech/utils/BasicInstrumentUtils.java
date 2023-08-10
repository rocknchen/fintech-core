package com.hthk.fintech.utils;

import com.hthk.fintech.enumration.InstrumentGroup;
import com.hthk.fintech.model.instrument.BasicInstrument;
import com.hthk.fintech.model.instrument.IInstrument;
import com.hthk.fintech.model.instrument.Instrument;

public class BasicInstrumentUtils {

    public static IInstrument build(String name) {

        BasicInstrument basicInst = new BasicInstrument();
        basicInst.setBasicInstrumentGroup(InstrumentGroup.BASIC);
        basicInst.setName(name);
        return basicInst;
    }

}
