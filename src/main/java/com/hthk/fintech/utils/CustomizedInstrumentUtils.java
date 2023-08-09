package com.hthk.fintech.utils;

import com.hthk.fintech.enumration.InstrumentGroup;
import com.hthk.fintech.model.instrument.IInstrument;
import com.hthk.fintech.model.instrument.Instrument;

public class CustomizedInstrumentUtils {

    public static IInstrument build(String name) {

        Instrument instrument = new Instrument();
        instrument.setInstrumentGroup(InstrumentGroup.CUSTOMIZATION);
        instrument.setName(name);
        return instrument;
    }
}
