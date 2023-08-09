package com.hthk.fintech.utils;

import com.hthk.fintech.enumration.InstrumentGroup;
import com.hthk.fintech.model.marketdata.quote.eod.EODQuote;

import java.time.LocalDate;

public class EODQuoteUtils {

    public static EODQuote buildInstrumentQuote(
            InstrumentGroup group, String name,
            LocalDate date, IInstrumentQuote quote) {
        return null;
    }

    public static EODQuote buildSecurityQuote(
            InstrumentGroup group, String name,
            LocalDate date, ISecurityQuote quote
    ) {
        return null;
    }

}
