package com.hthk.fintech.utils;

import com.hthk.fintech.model.marketdata.quote.eod.EODQuote;
import com.hthk.fintech.model.quote.IInstrumentQuote;
import com.hthk.fintech.model.quote.ISecurityQuote;

import java.time.LocalDate;

public class EODQuoteUtils {

    public static EODQuote buildCustomizedInstrumentQuote(
            String name, LocalDate date, IInstrumentQuote quote) {

        EODQuote eodQuote = new EODQuote();
        eodQuote.setQuoteDate(date);
        eodQuote.setInstrument(CustomizedInstrumentUtils.build(name));
//        eodQuote.set
        return eodQuote;
    }

    public static EODQuote buildSecurityQuote(
            String name, LocalDate date, ISecurityQuote quote
    ) {
        return null;
    }

}
