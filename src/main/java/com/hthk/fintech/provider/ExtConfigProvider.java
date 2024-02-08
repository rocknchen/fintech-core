package com.hthk.fintech.provider;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 15:39
 */
public interface ExtConfigProvider<T, R> extends ExtProvider<R> {

    default void init(T config) {
    }

}
