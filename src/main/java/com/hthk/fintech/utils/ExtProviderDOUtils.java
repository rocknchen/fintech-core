package com.hthk.fintech.utils;

import com.hthk.fintech.model.param.ExtProviderDO;
import com.hthk.fintech.model.param.impl.ExtProviderConfigStringSimple;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 15:56
 */
public class ExtProviderDOUtils {
    public static ExtProviderDO<ExtProviderConfigStringSimple> buildStringSimple(String value) {
        ExtProviderDO providerDO = new ExtProviderDO();
        providerDO.setName("stringSimple");
        providerDO.setConfig(new ExtProviderConfigStringSimple(value));
        return providerDO;
    }
}
