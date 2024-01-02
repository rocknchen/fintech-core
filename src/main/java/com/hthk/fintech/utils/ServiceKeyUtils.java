package com.hthk.fintech.utils;

import com.hthk.fintech.model.common.Service;
import com.hthk.fintech.model.common.ServiceKey;
import com.hthk.fintech.model.web.http.ActionTypeEnum;
import com.hthk.fintech.model.web.http.RequestEntity;
import org.springframework.util.StringUtils;

import static com.hthk.fintech.config.FintechStaticData.KEY_WORD_NA;

/**
 * @Author: Rock CHEN
 * @Date: 2024/1/2 18:54
 */
public class ServiceKeyUtils {

    public static ServiceKey build(Service service) {
        return new ServiceKey(
                service.action(),
                service.type(),
                getValue(service.subType1()),
                getValue(service.subType2()),
                getValue(service.subType3()),
                getValue(service.subType4()),
                getValue(service.subType5())
        );
    }

    public static ServiceKey build(ActionTypeEnum action, RequestEntity entity) {
        return new ServiceKey(
                action,
                entity.getType(),
                getValue(entity.getSubType1()),
                getValue(entity.getSubType2()),
                getValue(entity.getSubType3()),
                getValue(entity.getSubType4()),
                getValue(entity.getSubType5())
        );
    }

    private static String getValue(String orig) {
        boolean isEmpty = !StringUtils.hasText(orig) || orig.equals(KEY_WORD_NA);
        return isEmpty ? "" : orig;
    }


}
