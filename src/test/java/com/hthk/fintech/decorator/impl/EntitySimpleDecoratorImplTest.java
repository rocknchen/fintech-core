package com.hthk.fintech.decorator.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hthk.calypsox.model.staticdata.BookInfo;
import com.hthk.fintech.decorator.EntitySimpleDecorator;
import com.hthk.fintech.model.decorator.SimpleDecorateParam;
import com.hthk.fintech.model.param.ExtProviderDO;
import com.hthk.fintech.test.basic.AbstractAppContextTest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 14:20
 */
public class EntitySimpleDecoratorImplTest extends AbstractAppContextTest {

    private EntitySimpleDecorator decorator;

    @Before
    public void setup() {
        decorator = appContext.getBean(EntitySimpleDecorator.class);
    }

    @Test
    public void testGenerate_10_ID_ONLY() throws JsonProcessingException {

        List<String> valueList = Arrays.asList("name", "activity");
        List<ExtProviderDO> providerDOList = new ArrayList<>();
//        providerDOList.add()

        BookInfo bookInfo = buildBasic1();
        SimpleDecorateParam param = buildParam(valueList, providerDOList);

        bookInfo = decorator.process(bookInfo, param);

        logger.info("{}", toJson(bookInfo));
    }


    private SimpleDecorateParam buildParam(List<String> valueList, List<ExtProviderDO> providerDOList) {
        SimpleDecorateParam param = new SimpleDecorateParam();
        param.setValueList(valueList);
        param.setConnector("_");
        param.setExtProviderList(providerDOList);
        return param;
    }

    private BookInfo buildBasic1() {

        String name = "test";
        String activity = "testAct";
        String baseCcy = "CNH";

        BookInfo bookInfo = new BookInfo();
        bookInfo.setName(name);
        bookInfo.setActivity(activity);
        bookInfo.setBaseCurrency(baseCcy);
        return bookInfo;
    }

}