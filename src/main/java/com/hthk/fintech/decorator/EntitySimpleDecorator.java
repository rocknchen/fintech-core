package com.hthk.fintech.decorator;

import com.hthk.fintech.model.decorator.SimpleDecorateParam;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 14:16
 */
public interface EntitySimpleDecorator {

    <R> R process(R entity, SimpleDecorateParam param);

}
