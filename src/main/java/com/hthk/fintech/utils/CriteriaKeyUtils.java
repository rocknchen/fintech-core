package com.hthk.fintech.utils;

import com.hthk.fintech.model.common.Criteria;
import com.hthk.fintech.model.common.CriteriaKey;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/22 17:23
 */
public class CriteriaKeyUtils {

    public static CriteriaKey build(Criteria criteria) {
        return new CriteriaKey(
                criteria.action(),
                criteria.type(),
                criteria.subType1(), criteria.subType2(), criteria.subType3(), criteria.subType4(), criteria.subType5(),
                criteria.appName());
    }

}
