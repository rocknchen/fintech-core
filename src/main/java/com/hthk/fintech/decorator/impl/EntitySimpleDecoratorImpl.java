package com.hthk.fintech.decorator.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hthk.fintech.decorator.EntitySimpleDecorator;
import com.hthk.fintech.model.decorator.SimpleDecorateParam;
import com.hthk.fintech.model.param.ExtProviderDO;
import com.hthk.fintech.provider.ExtProvider;
import com.hthk.fintech.service.basic.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.LOG_DEFAULT;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 14:08
 */
@Component
public class EntitySimpleDecoratorImpl

        extends AbstractService

        implements EntitySimpleDecorator {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public <R> R process(R entity, SimpleDecorateParam param) {

        List<String> valueList = param.getValueList();
        List<ExtProviderDO> extProviderDOList = param.getExtProviderList();

        if (CollectionUtils.isEmpty(valueList) || CollectionUtils.isEmpty(extProviderDOList)) {
            logger.info("No decorate action: {} or {} empty", valueList, extProviderDOList);
            return entity;
        }

        String connector = param.getConnector();

        List<ExtProvider> extProviderList = get(extProviderDOList);
        logger.info(LOG_DEFAULT, "extProviderList", extProviderList);

        return entity;
    }

    private List<ExtProvider> get(List<ExtProviderDO> extProviderDOList) {
        return extProviderDOList.stream().map(t -> {
            try {
                return get(t);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private ExtProvider get(ExtProviderDO extProviderDO) throws JsonProcessingException {
        return customObjectMapper.readValue(extProviderDO, ExtProvider.class);
    }

}
