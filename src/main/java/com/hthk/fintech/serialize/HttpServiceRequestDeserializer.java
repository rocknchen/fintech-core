package com.hthk.fintech.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hthk.fintech.model.common.Criteria;
import com.hthk.fintech.model.common.CriteriaKey;
import com.hthk.fintech.model.software.app.ApplicationEnum;
import com.hthk.fintech.model.web.http.*;
import com.hthk.fintech.service.CriteriaAllocateService;
import com.hthk.fintech.service.impl.CriteriaAllocateServiceImpl;
import com.hthk.fintech.utils.CriteriaKeyUtils;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_PACKAGE;
import static com.hthk.fintech.config.FintechStaticData.LOG_WRAP;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/21 9:20
 */
public class HttpServiceRequestDeserializer<P, C> extends JsonDeserializer<HttpServiceRequest<P, C>> {

    private final static Logger logger = LoggerFactory.getLogger(HttpServiceRequestDeserializer.class);

    private DefaultObjectMapperFactory mapperFactory = new DefaultObjectMapperFactory();

    private static Map<ActionTypeEnum, Class<?>> actionParamMap;

    CriteriaAllocateService criteriaAllocateService = new CriteriaAllocateServiceImpl();

    private ObjectMapper objectMapper = mapperFactory.getObjectMapper();

    static {

        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(DEFAULT_PACKAGE));

        Set<Class<?>> supertypeSet = reflections.getTypesAnnotatedWith(HttpRequestParams.class);
        List<Class<?>> dataCriteriaList = supertypeSet.stream().filter(t -> t.getAnnotation(HttpRequestParams.class) != null).collect(Collectors.toList());
        actionParamMap = dataCriteriaList.stream().collect(Collectors.toMap(t -> {
            HttpRequestParams dataCriteria = t.getAnnotation(HttpRequestParams.class);
            return dataCriteria.name();
        }, Function.identity()));
    }

    @Override
    public HttpServiceRequest<P, C> deserialize(JsonParser parser, DeserializationContext deCont) throws IOException, JacksonException {

        JsonNode jsonTreeRoot = getJsonTreeRoot(parser);

        try {
            IRequestAction<?> action = deserializeAction(jsonTreeRoot);
            RequestDateTime requestDateTime = deserializeDateTime(jsonTreeRoot);
            RequestEntity requestEntity = deserializeRequestEntity(jsonTreeRoot);
            Object criteria = deserializeCriteria(action, requestEntity, jsonTreeRoot);

            return new HttpServiceRequest(
                    action,
                    requestDateTime,
                    requestEntity,
                    criteria);

        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private Object deserializeCriteria(IRequestAction<?> action, RequestEntity requestEntity, JsonNode root) throws JsonProcessingException {

        JsonNode criteriaNode = root.get("criteria");
        ApplicationEnum app = getApp(action);
        Class<?> criteriaClz = criteriaAllocateService.getClz(action.getName(), requestEntity, app);
        return objectMapper.treeToValue(criteriaNode, criteriaClz);
    }

    private ApplicationEnum getApp(IRequestAction<?> action) {
        return action.getParams() instanceof ApplicationSourceParams ? ((ApplicationSourceParams) action.getParams()).getApplicationSource() : null;
    }

    private RequestEntity deserializeRequestEntity(JsonNode root) throws JsonProcessingException {

        JsonNode dateTimeNode = root.get("entity");
        return objectMapper.treeToValue(dateTimeNode, RequestEntity.class);
    }

    private RequestDateTime deserializeDateTime(JsonNode root) throws JsonProcessingException {

        JsonNode dateTimeNode = root.get("dateTime");
        return objectMapper.treeToValue(dateTimeNode, RequestDateTime.class);
    }

    private IRequestAction<?> deserializeAction(JsonNode root) throws JsonProcessingException {

        String actionName = root.get("action").get("name").asText();
        ActionTypeEnum actionType = ActionTypeEnum.valueOf(actionName);
        logger.info("HTTP_REQUEST_ACTION_TYPE: {}", actionType);

        Class paramsClz = actionParamMap.get(actionType);
        logger.info("PARAMS_CLASS: {}", paramsClz);

        JsonNode paramsNode = root.get("action").get("params");
        Object params = null;
        try {
            params = objectMapper.treeToValue(paramsNode, paramsClz);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw e;
        }
        return new IRequestAction(actionType, params);
    }

    private JsonNode getJsonTreeRoot(JsonParser parser) throws IOException {

        ObjectCodec codec = parser.getCodec();
        return codec.readTree(parser);
    }

}
