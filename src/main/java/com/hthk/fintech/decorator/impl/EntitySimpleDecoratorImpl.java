package com.hthk.fintech.decorator.impl;

import com.hthk.fintech.decorator.EntitySimpleDecorator;
import com.hthk.fintech.model.decorator.SimpleDecorateParam;
import com.hthk.fintech.model.param.ExtProviderDO;
import com.hthk.fintech.provider.ExtProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 14:08
 */
@Component
public class EntitySimpleDecoratorImpl implements EntitySimpleDecorator {

    @Override
    public <R> R process(R entity, SimpleDecorateParam param) {

        List<String> valueList = param.getValueList();
        List<ExtProviderDO> extProviderDOList = param.getExtProviderList();

        if (CollectionUtils.isEmpty(valueList) || CollectionUtils.isEmpty(extProviderDOList)) {
            return entity;
        }

        String connector = param.getConnector();

        List<ExtProvider> extProviderList = get(extProviderDOList);

        return entity;
    }

    private List<ExtProvider> get(List<ExtProviderDO> extProviderDOList) {
        return null;
    }

}
