package com.hthk.fintech.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hthk.fintech.model.web.http.*;
import com.hthk.fintech.structure.utils.JacksonUtils;
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
import static com.hthk.fintech.config.FintechStaticData.LOG_DEFAULT;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/21 9:20
 */
public class HttpServiceRequestDeserializer<P, C> extends JsonDeserializer<HttpServiceRequest<P, C>> {

    private final static Logger logger = LoggerFactory.getLogger(HttpServiceRequestDeserializer.class);

    private DefaultObjectMapperFactory mapperFactory = new DefaultObjectMapperFactory();

    private static Map<HttpRequestActionTypeEnum, Class<?>> actionParamMap;

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

        HttpServiceRequest httpServiceRequest = new HttpServiceRequest();

        JsonNode jsonTreeRoot = getJsonTreeRoot(parser);
        IRequestAction<?> action = deserializeAction(jsonTreeRoot);
        logger.info(LOG_DEFAULT, "action", JacksonUtils.toYMLPrettyTry(action));

        return httpServiceRequest;
    }

    private IRequestAction<?> deserializeAction(JsonNode root) throws JsonProcessingException {

        String actionName = root.get("action").get("name").asText();
        HttpRequestActionTypeEnum actionType = HttpRequestActionTypeEnum.valueOf(actionName);
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
