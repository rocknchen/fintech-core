package com.hthk.fintech.provider;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 11:24
 */
public interface KeyManualProvider<R, P> extends KeyProvider<R> {

    P init(P param);

}
