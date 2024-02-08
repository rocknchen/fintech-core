package com.hthk.fintech.decorator;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 11:56
 */
public interface EntityDecorator<R, P> {

    R process(R entity, P param);

}
