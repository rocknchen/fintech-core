package com.hthk.fintech.provider.impl;

import com.hthk.common.utils.KeyIntegerUtils;
import com.hthk.fintech.model.math.DigitOffset;
import com.hthk.fintech.provider.KeyManualProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 11:26
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class KeyIntegerManualProvider implements KeyManualProvider<long[], DigitOffset> {

    private DigitOffset digitOffset;

    @Override
    public DigitOffset init(DigitOffset param) {
        return this.digitOffset = param;
    }

    @Override
    public long[] process(int count) {
        long[] result = KeyIntegerUtils.generate(digitOffset.getStart(), digitOffset.getOffSet(), count);
        digitOffset.setStart(digitOffset.getStart() + digitOffset.getOffSet() * count);
        return result;
    }

}
