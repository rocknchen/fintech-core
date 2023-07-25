package com.hthk.fintech.structure.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hthk.common.utils.ClassPathFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_DATE_FORMAT;
import static com.hthk.fintech.config.FintechStaticData.DEFAULT_DATE_TIME_FORMAT;

public class JacksonUtils {

    private final static Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    private static ObjectMapper jsonMapper;

    private static ObjectMapper xmlMapper;

    private static ObjectMapper ymlMapper;

    static {
        jsonMapper = buildGenericJson();
        xmlMapper = buildGenericXML();
        ymlMapper = buildGenericYML();
    }

    public static ObjectMapper buildGenericJson() {

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        mapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);

        registerJavaTimeModule(mapper);

        return mapper;

    }

    public static ObjectMapper buildGenericXML() {

        ObjectMapper xmlMapper = new XmlMapper();

        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        xmlMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        xmlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);

        registerJavaTimeModule(xmlMapper);

        return xmlMapper;

    }

    public static ObjectMapper buildGenericYML() {

        ObjectMapper ymlMapper = new YAMLMapper();

        ymlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ymlMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ymlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);

        registerJavaTimeModule(ymlMapper);

        return ymlMapper;

    }

    public static String readNote(
            String reqJson,
            String nodeName
    ) throws IOException {
        JsonNode jsonNode = jsonMapper.readTree(reqJson);
        JsonNode specNode = jsonNode.findValue(nodeName);
        if (specNode == null) {
            return null;
        }
        String origJson = specNode.toString();
        String prettyJson = convertPretty(origJson);
        return prettyJson;
    }

    public static String readYMLNote(
            String reqYML,
            String nodeName
    ) throws IOException {
        JsonNode jsonNode = ymlMapper.readTree(reqYML);
        JsonNode specNode = jsonNode.findValue(nodeName);
        if (specNode == null) {
            return null;
        }
        String origJson = specNode.toString();
        String prettyJson = convertPretty(origJson);
        return prettyJson;
    }

    public static JsonNode readXmlNote(
            String reqXML
    ) throws IOException {
        return xmlMapper.readTree(reqXML);
    }

    public static JsonNode readXmlNoteIncludeRoot(
            String reqXML
    ) throws IOException {
        String xmlStr;
        if (reqXML.indexOf("?>") != -1) {
            xmlStr = reqXML.substring(reqXML.indexOf("?>") + 2);
        } else {
            xmlStr = reqXML;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<root>").append(xmlStr).append("</root>");
        return xmlMapper.readTree(sb.toString());
    }

    public static <T> T readValue(
            String jsonStr,
            Class<T> clz
    ) throws IOException {
        return jsonMapper.readValue(jsonStr, clz);
    }

    public static <T> T readYml(
            String ymlStr,
            Class<T> clz
    ) throws IOException {
        return ymlMapper.readValue(ymlStr, clz);
    }

    public static <T> T readXML(
            String xmlStr,
            Class<T> clz
    ) throws IOException {
        return xmlMapper.readValue(xmlStr, clz);
    }

    public static String convertPretty(String origJson) throws IOException {
        Object origObj = jsonMapper.readValue(origJson, Object.class);
        return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(origObj);
    }

    public static <T> String toJsonPretty(T origModel) throws IOException {
        return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(origModel);
    }

    public static <T> String toJsonPrettyTry(T origModel) {
        try {
            return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(origModel);
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
    }

    public static <T> String toYMLPrettyTry(T origModel) {
        try {
            return ymlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(origModel);
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
    }

    public static <T> String toXMLPretty(T origModel) throws IOException {
        return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(origModel);
    }

    public static <T> String toXMLPrettyTry(T origModel) {
        try {
            return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(origModel);
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T convertClassPathFileToModel(
            String classpathFile,
            Class<T> clz
    ) throws IOException {
        String json = ClassPathFileUtils.readFileAsStr(classpathFile);
        T model = jsonMapper.readValue(json, clz);
        return model;
    }

    private static void registerJavaTimeModule(ObjectMapper mapper) {

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        DateTimeFormatter df = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(df));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(df));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dtf));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dtf));

        mapper.registerModule(javaTimeModule);

    }

    public static <T, R> R clone(
            T src,
            Class<R> clz
    ) throws IOException {
        return jsonMapper.readValue(jsonMapper.writeValueAsString(src), clz);
    }

    public static String getStringValue(
            TreeNode node,
            String name
    ) {
        return node.get(name).toString().replaceAll("\"", "");
    }

}
