package com.hthk.fintech.utils;

import com.hthk.fintech.model.common.Criteria;
import com.hthk.fintech.model.common.CriteriaKey;
import com.hthk.fintech.model.web.http.ActionTypeEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/22 17:23
 */
public class CriteriaKeyUtils {

    public static List<CriteriaKey> build(Criteria criteria) {

        List<ActionTypeEnum> actionList = Arrays.stream(criteria.actions()).collect(Collectors.toList());
        return actionList.stream().map(t -> new CriteriaKey(
                t,
                criteria.type(),
                criteria.subType1(), criteria.subType2(), criteria.subType3(), criteria.subType4(), criteria.subType5(),
                criteria.appName())).collect(Collectors.toList());
    }

}
