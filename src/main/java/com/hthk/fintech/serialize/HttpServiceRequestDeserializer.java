package com.hthk.fintech.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hthk.fintech.model.web.http.HttpRequestActionTypeEnum;
import com.hthk.fintech.model.web.http.HttpRequestParams;
import com.hthk.fintech.model.web.http.HttpServiceRequest;
import com.hthk.fintech.model.web.http.IRequestAction;
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

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/21 9:20
 */
public class HttpServiceRequestDeserializer<P, C> extends JsonDeserializer<HttpServiceRequest<P, C>> {

    private final static Logger logger = LoggerFactory.getLogger(HttpServiceRequestDeserializer.class);

    private DefaultObjectMapperFactory mapperFactory = new DefaultObjectMapperFactory();

    private static Map<HttpRequestActionTypeEnum, Class<? extends HttpRequestParams>> actionParamMap;

    private ObjectMapper objectMapper = mapperFactory.getObjectMapper();

    static {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(DEFAULT_PACKAGE));
        Set<Class<? extends HttpRequestParams>> supertypeSet = reflections.getSubTypesOf(HttpRequestParams.class);
        List<Class<? extends HttpRequestParams>> dataCriteriaList = supertypeSet.stream().filter(t -> t.getAnnotation(HttpRequestParams.class) != null).collect(Collectors.toList());
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

        return httpServiceRequest;
    }

    private IRequestAction<?> deserializeAction(JsonNode root) {

        String actionName = root.get("action").get("name").asText();
        logger.info("HTTP_REQUEST_ACTION_TYPE: {}", actionName);
        return null;
    }

    private JsonNode getJsonTreeRoot(JsonParser parser) throws IOException {

        ObjectCodec codec = parser.getCodec();
        return codec.readTree(parser);
    }

}
