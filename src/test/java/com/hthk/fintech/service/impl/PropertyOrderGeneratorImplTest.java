package com.hthk.fintech.service.impl;

import com.hthk.fintech.service.PropertyOrderGenerator;
import org.junit.Test;

/**
 * @Author: Rock CHEN
 * @Date: 2024/1/8 15:18
 */
public class PropertyOrderGeneratorImplTest {

    PropertyOrderGenerator generator = new PropertyOrderGeneratorImpl();

    @Test
    public void testGenerate_JACKSON() {

        String nameSpace = "JsonPropertyOrder";
        String sourceFile = "C:/Rock/Datas/IT/DEV/ws/WorkSpaceIDEAHTSFICC/calypso-model/src/main/java/com/hthk/calypsox/model/trade/TradeInfo.java";
//        String sourceFile = "C:/Rock/Datas/IT/DEV/ws/WorkSpaceIDEAHTSFICC/calypso-model/src/main/java/com/hthk/calypsox/model/position/CashPositionInfo.java";
//        String sourceFile = "C:/Rock/Datas/IT/DEV/ws/WorkSpaceIDEAHTSFICC/calypso-model/src/main/java/com/hthk/calypsox/model/staticdata/future/contract/FutureInfo.java";

//        String sourceFile = "C:/Rock/Datas/IT/DEV/ws/WorkSpaceIDEAHTSFICC/calypso-model/src/main/java/com/hthk/calypsox/model/trade/product/FutureFXTradeInfo.java";
//        String sourceFile = "C:/Rock/Datas/IT/DEV/ws/WorkSpaceIDEAHTSFICC/calypso-model/src/main/java/com/hthk/calypsox/model/marketdata/quote/eod/EODQuote.java";
        int offSet = 3;

        String output = generator.process(nameSpace, sourceFile, offSet);
        System.out.println(output);
    }

    @Test
    public void testGenerate_FieldOrder() {

        String nameSpace = "FieldOrder";
//        String sourceFile = "C:/Rock/Datas/IT/DEV/ws/WorkSpaceIDEAHTSFICC/fintech-model/src/main/java/com/hthk/fintech/model/trade/TradeInfo.java";
        String sourceFile = "C:/Rock/Datas/IT/DEV/ws/WorkSpaceIDEAHTSFICC/fintech-model/src/main/java/com/hthk/fintech/model/staticdata/BookInfo.java";
        int offSet = 3;

        String output = generator.process(nameSpace, sourceFile, offSet);
        System.out.println(output);
    }

}