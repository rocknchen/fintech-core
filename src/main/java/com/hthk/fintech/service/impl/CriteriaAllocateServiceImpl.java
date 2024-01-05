package com.hthk.fintech.service.impl;

import com.hthk.fintech.model.common.Criteria;
import com.hthk.fintech.model.common.CriteriaKey;
import com.hthk.fintech.model.data.datacenter.query.EntityTypeEnum;
import com.hthk.fintech.model.software.app.ApplicationEnum;
import com.hthk.fintech.model.web.http.ActionTypeEnum;
import com.hthk.fintech.model.web.http.RequestEntity;
import com.hthk.fintech.service.CriteriaAllocateService;
import com.hthk.fintech.structure.utils.JacksonUtils;
import com.hthk.fintech.utils.CriteriaKeyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.*;

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

        logger.debug(LOG_WRAP, "CRITERIA_CLASS_MAP", JacksonUtils.toYMLPrettyTry(criteriaClzMap));
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

        Map<CriteriaKey, Class<?>> matchClzMap = getMatchClzMap(criteriaClzMap, action, requestEntity, app);
        logger.info(LOG_WRAP, "MATCHED_CRITERIA_CLASS_MAP", JacksonUtils.toYMLPrettyTry(matchClzMap));

        CriteriaKey criteriaKey = getTheBest(matchClzMap, action, requestEntity, app);
        logger.info(LOG_DEFAULT, "BEST_CRITERIA_CLASS", JacksonUtils.toYMLPrettyTry(criteriaKey));
        return matchClzMap.get(criteriaKey);
    }

    private CriteriaKey getTheBest(Map<CriteriaKey, Class<?>> matchClzMap, ActionTypeEnum action, RequestEntity requestEntity, ApplicationEnum app) {

        if (app == null) {
            return getCriteriaWithOutApp(matchClzMap, action, requestEntity);
        } else {
            return matchClzMap.keySet().stream().filter(t -> t.getAction() == action && t.getType() == requestEntity.getType() && t.getAppName() == app).findFirst()
                    .orElse(getCriteriaWithOutApp(matchClzMap, action, requestEntity));
        }
    }

    private CriteriaKey getCriteriaWithOutApp(Map<CriteriaKey, Class<?>> matchClzMap, ActionTypeEnum action, RequestEntity requestEntity) {
        return matchClzMap.keySet().stream().filter(t -> t.getAction() == action && t.getType() == requestEntity.getType() && t.getAppName() == ApplicationEnum.NA).findFirst().orElse(null);
    }

    private Map<CriteriaKey, Class<?>> getMatchClzMap(Map<CriteriaKey, Class<?>> criteriaClzMap, ActionTypeEnum action, RequestEntity requestEntity, ApplicationEnum app) {

        Map<CriteriaKey, Class<?>> matchClzMap = new HashedMap();
        criteriaClzMap.forEach((k, v) -> {
            if (isMatch(v, action, requestEntity, app)) {
                matchClzMap.put(k, v);
            }
        });
        return matchClzMap;
    }

    private boolean isMatch(Class<?> clz, ActionTypeEnum action, RequestEntity requestEntity, ApplicationEnum app) {
        Criteria criteria = clz.getAnnotation(Criteria.class);
        List<ActionTypeEnum> criteriaActionList = Arrays.stream(criteria.actions()).collect(Collectors.toList());
        EntityTypeEnum criteriaType = criteria.type();
        if (criteriaActionList.contains(action) && criteriaType == requestEntity.getType()) {
            return true;
        } else {
            return false;
        }
    }

}
