package com.hthk.fintech.utils;

import com.csvreader.CsvWriter;
import com.hthk.common.utils.CustomCollectionUtils;
import com.hthk.fintech.enumration.CSVField;
import com.hthk.fintech.enumration.FieldOrder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_DATE_TIME_FORMAT_FORMATTER;
import static com.hthk.fintech.config.FintechStaticData.DEFAULT_FILE_CHARSET_NAME;

public class CSVUtils {

    public static void write(List<?> dtoList, String outputFile, String charsetName, boolean isMDs, boolean isForce, Class<?> csvDTOClz) throws IOException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (isMDs) {
            new File(outputFile).getParentFile().mkdirs();
        }
        String realCharsetName = Optional.ofNullable(charsetName).orElse(DEFAULT_FILE_CHARSET_NAME);
        write(dtoList, outputFile, realCharsetName, isForce, csvDTOClz);
    }

    public static void write(List<?> dtoList, String outputFile, String charsetName, boolean isMDs) throws IOException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (isMDs) {
            new File(outputFile).getParentFile().mkdirs();
        }
        String realCharsetName = Optional.ofNullable(charsetName).orElse(DEFAULT_FILE_CHARSET_NAME);
        write(dtoList, outputFile, realCharsetName, false, null);
    }

    public static void write(List<?> dtoList, String outputFile, String charsetName, boolean isForce, Class<?> csvDTOClz) throws IOException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        boolean isEmpty = dtoList == null || dtoList.size() == 0;
        if (!isForce && isEmpty) {
            return;
        }

        new File(outputFile).getParentFile().mkdirs();

        CsvWriter writer = new CsvWriter(outputFile, ',', Charset.forName(charsetName));

        if (isEmpty) {
            List<Field> fieldList = getFieldList(csvDTOClz);
            List<CSVField> csvFieldAnnoList = getCSVFieldAnnoList(fieldList);
            List<String> headerStrList = getHeaderStrList(csvFieldAnnoList);
            writer.writeRecord(CustomCollectionUtils.toArrayStr(headerStrList), false);
            writer.close();
            return;
        }

//        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));

        List<Field> fieldList = getFieldList(dtoList);
        List<CSVField> csvFieldAnnoList = getCSVFieldAnnoList(fieldList);
        List<Method> methodList = getMethodList(dtoList.get(0), fieldList);
        List<String> headerStrList = buildHeaderStrList(dtoList, csvFieldAnnoList);
        List<List<String>> contentList = buildContentList(dtoList, fieldList, methodList);

        writer.writeRecord(CustomCollectionUtils.toArrayStr(headerStrList), false);
        for (int i = 0; i < contentList.size(); i++) {
            writer.writeRecord(CustomCollectionUtils.toArrayStr(contentList.get(i)), false);
        }

        writer.close();
    }

    private static List<Method> getMethodList(Object obj, List<Field> fieldList) throws NoSuchMethodException {

        List<Method> methodList = new ArrayList<>();
        for (int i = 0; i < fieldList.size(); i++) {
            String name = fieldList.get(i).getName();
            String funName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = obj.getClass().getMethod(funName);
            methodList.add(method);
        }
        return methodList;
    }

    private static List<Field> getFieldList(Class<?> clz) throws NoSuchFieldException {
        FieldOrder order = clz.getAnnotation(FieldOrder.class);
        List<String> orderList = CustomCollectionUtils.toList(order.value());
        List<Field> fieldList = getFieldList(clz, orderList);
        return fieldList;
    }

    private static List<Field> getFieldList(List<?> dtoList) throws NoSuchFieldException {

        Object obj = dtoList.get(0);
        FieldOrder order = obj.getClass().getAnnotation(FieldOrder.class);
        List<String> orderList = CustomCollectionUtils.toList(order.value());
        List<Field> fieldList = getFieldList(obj, orderList);
        return fieldList;
    }

    private static List<CSVField> getCSVFieldAnnoList(List<Field> fieldList) throws NoSuchFieldException {

        List<CSVField> fieldAnnoList = getFieldAnnoList(fieldList);
        return fieldAnnoList;
    }

    private static List<List<String>> buildContentList(List<?> dtoList, List<Field> fieldList, List<Method> methodList) throws InvocationTargetException, IllegalAccessException {

        List<List<String>> contentList = new ArrayList<>();
        for (int i = 0; i < dtoList.size(); i++) {
            List<String> outputInfoList = convertList(dtoList.get(i), fieldList, methodList);
            contentList.add(outputInfoList);
        }
        return contentList;
    }

    private static List<String> convertList(Object obj, List<Field> fieldList, List<Method> methodList) throws InvocationTargetException, IllegalAccessException {

        List<String> result = new ArrayList<>();
        for (int i = 0; i < methodList.size(); i++) {
            Method method = methodList.get(i);
            Object object = method.invoke(obj);
            if (object == null) {
                result.add("");
            }
            if (object instanceof String) {
                result.add((String) method.invoke(obj));
            }
            if (object instanceof BigDecimal) {
                result.add(((BigDecimal) method.invoke(obj)).toPlainString());
            }
            if (object instanceof LocalDate) {
                result.add(((LocalDate) method.invoke(obj)).format(DateTimeFormatter.ISO_DATE));
            }
            if (object instanceof LocalDateTime) {
                result.add(((LocalDateTime) method.invoke(obj)).format(DEFAULT_DATE_TIME_FORMAT_FORMATTER));
            }
        }
        return result;
    }

    private static List<String> buildHeaderStrList(List<?> dtoList, List<CSVField> csvFieldAnnoList) throws NoSuchFieldException {

        List<String> headerList = new ArrayList<>();
        if (dtoList == null || dtoList.size() == 0) {
            return headerList;
        }
        return getHeaderStrList(csvFieldAnnoList);
    }

    private static List<String> getHeaderStrList(List<CSVField> csvFieldAnnoList) throws NoSuchFieldException {

        List<String> columnHeaderList = new ArrayList<>();
        for (int i = 0; i < csvFieldAnnoList.size(); i++) {
            columnHeaderList.add(csvFieldAnnoList.get(i).header());
        }
        return columnHeaderList;
    }

    private static List<CSVField> getFieldAnnoList(List<Field> fieldList) {

        List<CSVField> fieldAnnoList = new ArrayList<>();
        for (int i = 0; i < fieldList.size(); i++) {
            fieldAnnoList.add(fieldList.get(i).getAnnotation(CSVField.class));
        }
        return fieldAnnoList;
    }

    private static List<Field> getFieldList(Class<?> clz, List<String> orderList) throws NoSuchFieldException {

        List<Field> fieldList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            String fieldName = orderList.get(i);
            Field field = clz.getDeclaredField(fieldName);
            fieldList.add(field);
        }
        return fieldList;
    }

    private static List<Field> getFieldList(Object obj, List<String> orderList) throws NoSuchFieldException {

        List<Field> fieldList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            String fieldName = orderList.get(i);
            Field field = obj.getClass().getDeclaredField(fieldName);
            fieldList.add(field);
        }
        return fieldList;
    }

}
