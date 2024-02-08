package com.hthk.fintech.decorator.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hthk.calypsox.model.staticdata.BookInfo;
import com.hthk.fintech.decorator.EntitySimpleDecorator;
import com.hthk.fintech.model.decorator.SimpleDecorateParam;
import com.hthk.fintech.model.param.ExtProviderDO;
import com.hthk.fintech.test.basic.AbstractAppContextTest;
import com.hthk.fintech.utils.ExtProviderDOUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hthk.fintech.config.FintechStaticData.LOG_DEFAULT;
import static com.hthk.fintech.config.FintechStaticData.LOG_WRAP;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 14:20
 */
public class EntitySimpleDecoratorImplTest extends AbstractAppContextTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private EntitySimpleDecorator decorator;

    @Before
    public void setup() {
        decorator = appContext.getBean(EntitySimpleDecorator.class);
    }

    @Test
    public void testGenerate_1() throws JsonProcessingException {

        List<String> valueList = Arrays.asList("name", "activity");
        List<ExtProviderDO> providerDOList = new ArrayList<>();
        providerDOList.add(buildStringSimple("TEST"));

        logger.debug(LOG_WRAP, "extProviderDOList", toJson(providerDOList));

        BookInfo bookInfo = buildBasic1();
        SimpleDecorateParam param = buildParam(valueList, providerDOList);

        bookInfo = decorator.process(bookInfo, param);

        logger.info("{}", toJson(bookInfo));
    }

    private ExtProviderDO buildIntegerSimple(long start, int offSet) {
        return null;
    }

    private ExtProviderDO buildStringSimple(String value) {
        return ExtProviderDOUtils.buildStringSimple(value);
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