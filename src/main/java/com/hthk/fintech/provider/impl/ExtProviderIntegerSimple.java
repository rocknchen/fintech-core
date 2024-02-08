package com.hthk.fintech.provider.impl;

import com.hthk.common.utils.KeyIntegerUtils;
import com.hthk.fintech.model.param.impl.ExtProviderConfigIntegerSimple;
import com.hthk.fintech.provider.ExtConfigStringProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 15:24
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExtProviderIntegerSimple

        implements ExtConfigStringProvider<ExtProviderConfigIntegerSimple> {

    private long start;

    private int offSet;

    private long current;

    @Override
    public void init(ExtProviderConfigIntegerSimple config) {
        this.start = config.getStart();
        this.current = start;
        this.offSet = config.getOffSet();
    }

    @Override
    public String process() {

        long generated = KeyIntegerUtils.generate(current, offSet, 1)[0];
        current = generated++;
        return Long.valueOf(generated).toString();
    }

}
