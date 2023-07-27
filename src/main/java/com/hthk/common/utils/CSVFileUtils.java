package com.hthk.common.utils;

import com.csvreader.CsvWriter;
import com.hthk.fintech.enumration.CSVField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CSVFileUtils {

    public static <T> List<List<String>> convertInfo(List<T> tradeList, List<Map<String, Method>> headerList) {
        return tradeList.stream().map(t -> CSVFileUtils.convertSubInfo(t, headerList)).collect(Collectors.toList());
    }

    public static <T> List<String> convertSubInfo(T uploaderModel, List<Map<String, Method>> headerList) {

        List<String> infoList = new ArrayList<>();
        headerList.forEach(map -> {
            Method getMethod = map.values().stream().findFirst().get();
            try {
                String info = (getMethod.invoke(uploaderModel)).toString();
                infoList.add(info);
            } catch (Exception e) {
                infoList.add(null);
            }
        });
        return infoList;
    }

    public static void write(List<Map<String, Method>> headerList, List<List<String>> infoList, String destFile) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(destFile, false));
        CsvWriter writer = new CsvWriter(bw, ',');
        List<String> headerStrList = headerList.stream().map(t -> t.keySet().stream().findFirst().get()).collect(Collectors.toList());
        writer.writeRecord(CustomCollectionUtils.toArrayStr(headerStrList), false);
        infoList.forEach(t -> {
            try {
                writer.writeRecord(CustomCollectionUtils.toArrayStr(t), false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.close();
    }


//    public static void write(List<?> dtoList, String outputFile) throws IOException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//
//        if (dtoList == null || dtoList.size() == 0) {
//            return;
//        }
//
//        new File(outputFile).getParentFile().mkdirs();
//
//        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
//        CsvWriter writer = new CsvWriter(bw, ',');
//
//        List<Field> fieldList = getFieldList(dtoList);
//        List<CSVField> csvFieldAnnoList = getCSVFieldAnnoList(fieldList);
//        List<Method> methodList = getMethodList(dtoList.get(0), fieldList);
//        List<String> headerStrList = buildHeaderStrList(dtoList, csvFieldAnnoList);
//        List<List<String>> contentList = buildContentList(dtoList, fieldList, methodList);
//
//        writer.writeRecord(CustomCollectionUtils.toArrayStr(headerStrList), false);
//        for (int i = 0; i < contentList.size(); i++) {
//            writer.writeRecord(CustomCollectionUtils.toArrayStr(contentList.get(i)), false);
//        }
//
//        writer.close();
//    }

    public static List<Method> getMethodList(Object obj, List<Field> fieldList) throws NoSuchMethodException {

        List<Method> methodList = new ArrayList<>();
        for (int i = 0; i < fieldList.size(); i++) {
            String name = fieldList.get(i).getName();
            String funName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = obj.getClass().getMethod(funName);
            methodList.add(method);
        }
        return methodList;
    }

    public static List<CSVField> getCSVFieldAnnoList(List<Field> fieldList) throws NoSuchFieldException {

        List<CSVField> fieldAnnoList = getFieldAnnoList(fieldList);
        return fieldAnnoList;
    }

    public static List<List<String>> buildContentList(List<?> dtoList, List<Field> fieldList, List<Method> methodList) throws InvocationTargetException, IllegalAccessException {

        List<List<String>> contentList = new ArrayList<>();
        for (int i = 0; i < dtoList.size(); i++) {
            List<String> outputInfoList = convertList(dtoList.get(i), fieldList, methodList);
            contentList.add(outputInfoList);
        }
        return contentList;
    }

    public static List<String> convertList(Object obj, List<Field> fieldList, List<Method> methodList) throws InvocationTargetException, IllegalAccessException {

        List<String> result = new ArrayList<>();
        for (int i = 0; i < methodList.size(); i++) {
            Method method = methodList.get(i);
            result.add((String) method.invoke(obj));
        }
        return result;
    }

    public static List<String> buildHeaderStrList(List<?> dtoList, List<CSVField> csvFieldAnnoList) throws NoSuchFieldException {

        List<String> headerList = new ArrayList<>();
        if (dtoList == null || dtoList.size() == 0) {
            return headerList;
        }
        return getHeaderStrList(dtoList.get(0), csvFieldAnnoList);
    }

    public static List<String> getHeaderStrList(Object obj, List<CSVField> csvFieldAnnoList) throws NoSuchFieldException {

        List<String> columnHeaderList = new ArrayList<>();
        for (int i = 0; i < csvFieldAnnoList.size(); i++) {
            columnHeaderList.add(csvFieldAnnoList.get(i).header());
        }
        return columnHeaderList;
    }

    public static List<CSVField> getFieldAnnoList(List<Field> fieldList) {

        List<CSVField> fieldAnnoList = new ArrayList<>();
        for (int i = 0; i < fieldList.size(); i++) {
            fieldAnnoList.add(fieldList.get(i).getAnnotation(CSVField.class));
        }
        return fieldAnnoList;
    }

    public static List<Field> getFieldList(Object obj, List<String> orderList) throws NoSuchFieldException {

        List<Field> fieldList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            String fieldName = orderList.get(i);
            Field field = obj.getClass().getDeclaredField(fieldName);
            fieldList.add(field);
        }
        return fieldList;
    }

}