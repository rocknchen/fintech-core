package com.hthk.fintech.utils;

import com.hthk.fintech.enumration.BasicInstrumentGroupEnum;
import com.hthk.fintech.enumration.QuoteTypeEnum;
import com.hthk.fintech.enumration.SecurityGroupEnum;
import com.hthk.fintech.model.marketdata.quote.eod.EODInstrumentQuote;
import com.hthk.fintech.model.quote.BasicInstrumentQuote;
import com.hthk.fintech.model.quote.SecurityQuote;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EODQuoteUtils {

    public static EODInstrumentQuote buildBasicInstrumentQuote(
            BasicInstrumentGroupEnum instGroup, String name, LocalDate date, BigDecimal close) {

        EODInstrumentQuote eodQuote = new EODInstrumentQuote();

        eodQuote.setInstrument(BasicInstrumentUtils.build(instGroup, name));

        eodQuote.setDate(date);

        BasicInstrumentQuote quote = new BasicInstrumentQuote();
        quote.setQuoteType(QuoteTypeEnum.PRICE);
        quote.setClose(close);
        eodQuote.setQuote(quote);

        return eodQuote;
    }

    public static EODInstrumentQuote buildSecurityQuoteCloseOnly(
            SecurityGroupEnum secGroup, String name, LocalDate date, BigDecimal close) {

        EODInstrumentQuote eodQuote = new EODInstrumentQuote();

        eodQuote.setInstrument(SecurityInstrumentUtils.build(secGroup, name));

        eodQuote.setDate(date);

        SecurityQuote quote = new SecurityQuote();
        quote.setQuoteType(QuoteTypeEnum.PRICE);
        quote.setClose(close);
        eodQuote.setQuote(quote);

        return eodQuote;
    }

}
