package com.hthk.fintech.provider.impl;

import com.hthk.fintech.model.param.impl.ExtProviderConfigStringSimple;
import com.hthk.fintech.provider.ExtConfigStringProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 15:29
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExtProviderStringSimple

        implements ExtConfigStringProvider<ExtProviderConfigStringSimple> {

    private String value;

    @Override
    public void init(ExtProviderConfigStringSimple config) {
        this.value = config.getValue();
    }

    @Override
    public String process() {
        return value;
    }

}