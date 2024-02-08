package com.hthk.fintech.decorator.impl;

import com.hthk.fintech.decorator.EntitySimpleDecorator;
import com.hthk.fintech.model.decorator.SimpleDecorateParam;
import org.springframework.stereotype.Component;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 14:08
 */
@Component
public class EntitySimpleDecoratorImpl implements EntitySimpleDecorator {

    @Override
    public <R> R process(R entity, SimpleDecorateParam param) {
        return entity;
    }

}
