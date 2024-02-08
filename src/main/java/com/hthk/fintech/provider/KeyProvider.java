package com.hthk.fintech.provider;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 11:24
 */
public interface KeyProvider<R> {

    R process(int count);

}
