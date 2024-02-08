package com.hthk.fintech.provider;

import com.hthk.fintech.model.math.DigitOffset;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 13:52
 */
public interface KeyIntegerManualProvider extends KeyManualProvider<long[], DigitOffset> {

    DigitOffset init(DigitOffset param);

    long[] process(int count);

}
