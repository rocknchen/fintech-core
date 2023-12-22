package com.hthk.fintech.service.impl;

import com.hthk.fintech.model.common.Criteria;
import com.hthk.fintech.model.common.CriteriaKey;
import com.hthk.fintech.model.software.app.ApplicationEnum;
import com.hthk.fintech.model.web.http.ActionTypeEnum;
import com.hthk.fintech.model.web.http.RequestEntity;
import com.hthk.fintech.service.CriteriaAllocateService;
import com.hthk.fintech.utils.CriteriaKeyUtils;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_PACKAGE;
import static com.hthk.fintech.config.FintechStaticData.LOG_WRAP;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/22 17:31
 */
public class CriteriaAllocateServiceImpl implements CriteriaAllocateService {

    private final static Logger logger = LoggerFactory.getLogger(CriteriaAllocateServiceImpl.class);

    private static Map<CriteriaKey, Class<?>> criteriaClzMap;

    static {

        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(DEFAULT_PACKAGE));

        Set<Class<?>> criteriaSet = reflections.getTypesAnnotatedWith(Criteria.class);
        List<Class<?>> criteriaList = criteriaSet.stream().filter(t -> t.getAnnotation(Criteria.class) != null).collect(Collectors.toList());
        criteriaClzMap = criteriaList.stream().collect(Collectors.toMap(t -> {
            Criteria dataCriteria = t.getAnnotation(Criteria.class);
            return CriteriaKeyUtils.build(dataCriteria);
        }, Function.identity()));

        logger.info(LOG_WRAP, "CRITERIA_CLASS_MAP", criteriaClzMap);
    }

    /**
     * TODO
     *
     * @param action
     * @param requestEntity
     * @param app
     * @return
     */
    @Override
    public Class getClz(ActionTypeEnum action, RequestEntity requestEntity, ApplicationEnum app) {

        return criteriaClzMap.values().iterator().next();
    }

}
