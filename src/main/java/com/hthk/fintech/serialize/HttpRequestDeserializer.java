package com.hthk.fintech.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hthk.fintech.model.data.datacenter.query.DataCriteria;
import com.hthk.fintech.model.data.datacenter.query.DataQueryRequest;
import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;
import com.hthk.fintech.model.web.http.HttpRequest;
import com.hthk.fintech.model.web.http.MirrorHttpRequest;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.*;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 20:04
 */
public class HttpRequestDeserializer<T extends IDataCriteria> extends JsonDeserializer<HttpRequest<T>> {

    private final static Logger logger = LoggerFactory.getLogger(HttpRequestDeserializer.class);

    private static Map<String, Class<? extends IDataCriteria>> iDataCriteriaMap;

    private DefaultObjectMapperFactory mapperFactory = new DefaultObjectMapperFactory();

    private ObjectMapper objectMapper = mapperFactory.getObjectMapper();

    static {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(DEFAULT_PACKAGE));
        Set<Class<? extends IDataCriteria>> supertypeSet = reflections.getSubTypesOf(IDataCriteria.class);
        List<Class<? extends IDataCriteria>> dataCriteriaList = supertypeSet.stream().filter(t -> t.getAnnotation(DataCriteria.class) != null).collect(Collectors.toList());
        iDataCriteriaMap = dataCriteriaList.stream().collect(Collectors.toMap(t -> {
            DataCriteria dataCriteria = t.getAnnotation(DataCriteria.class);
            return buildKey(dataCriteria);
        }, Function.identity()));
    }

    private static String buildKey(DataCriteria dataCriteria) {
        return buildKey(dataCriteria.sourceName().name(), dataCriteria.entityType().name());
    }

    private static String buildKey(String sourceName, String entityType) {
        return sourceName + "_" + entityType;
    }

    @Override
    public HttpRequest<T> deserialize(JsonParser parser, DeserializationContext deCont) throws IOException, JacksonException {

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        String sourceName = node.get("source").get("name").asText();
        String entityType = node.get("data").get("entity").get("type").asText();

        logger.info("\r\nsourceName: {}\r\nentityType: {}", sourceName, entityType);

        Class<T> clz = getIDataCriteriaClz(sourceName, entityType);
        logger.info(LOG_DEFAULT, "clz", clz);

        MirrorHttpRequest mirrorHttpRequest = objectMapper.treeToValue(node, MirrorHttpRequest.class);
        HttpRequest httpRequest = new HttpRequest();

        BeanUtils.copyProperties(mirrorHttpRequest, httpRequest);
        DataQueryRequest dqr = new DataQueryRequest();
        httpRequest.setData(dqr);
        BeanUtils.copyProperties(mirrorHttpRequest.getData(), dqr);

        IDataCriteria iDataCriteria = objectMapper.treeToValue(node.get("data").get("criteria"), clz);
        httpRequest.getData().setCriteria(iDataCriteria);

        return httpRequest;
    }

    private Class<T> getIDataCriteriaClz(String sourceName, String entityType) {
        return (Class<T>) iDataCriteriaMap.get(buildKey(sourceName, entityType));
    }

}
