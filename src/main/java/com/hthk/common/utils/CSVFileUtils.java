package com.hthk.common.utils;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.hthk.fintech.converter.AttributeStringConverter;
import com.hthk.fintech.enumration.CSVField;
import com.hthk.fintech.enumration.FieldOrder;
import com.hthk.fintech.event.utils.EventUtils;
import com.hthk.fintech.exception.ServiceException;
import com.hthk.fintech.model.file.csv.CSVFieldDTO;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.KW_GET;

public class CSVFileUtils {

    protected final static Logger logger = LoggerFactory.getLogger(CSVFileUtils.class);

    public static <T> void write(T dto, String outputFile, boolean force) throws IOException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        write(dto, outputFile, force, null);
    }

    public static <T> void write(T dto, String outputFile, boolean force, Class<?> clz) throws IOException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        writeList(Arrays.asList(dto), outputFile, force, clz);
    }

    public static void writeList(List<?> dtoList, String outputFile, boolean force) throws IOException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        writeList(dtoList, outputFile, force, null);
    }

    public static void writeList(List<?> dtoList, String outputFile, boolean force, Class<?> clz) throws IOException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (force) {
            new File(outputFile).getParentFile().mkdirs();
        }
        writeDTO(dtoList, outputFile, clz);
    }

    public static void writeDTO(List<?> dtoList, String outputFile, Class<?> clz) throws IOException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (dtoList == null || dtoList.size() == 0) {
            return;
        }

        new File(outputFile).getParentFile().mkdirs();

        CsvWriter writer = null;
        try {
            Class<?> modelClz = clz == null ? dtoList.get(0).getClass() : clz;

            List<String> fieldList = getFieldList(modelClz);
            Map<String, CSVFieldDTO> csvFieldDTOMap = convert(modelClz);

            List<String> headerStrList = buildHeaderStrList(fieldList, csvFieldDTOMap);
            List<List<String>> contentList = buildContentList(fieldList, csvFieldDTOMap, dtoList);

            LockUtils.lock();
            writer = null;

            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
            writer = new CsvWriter(bw, ',');

            writer.writeRecord(CustomCollectionUtils.toArrayStr(headerStrList), false);
            for (int i = 0; i < contentList.size(); i++) {
                writer.writeRecord(CustomCollectionUtils.toArrayStr(contentList.get(i)), false);
            }

            writer.close();
            LockUtils.unLock();

        } catch (Throwable e) {
            if (writer != null) {
                writer.close();
            }
            new File(outputFile).deleteOnExit();
            LockUtils.unLock();
            throw e;
        }
    }

    private static List<String> buildHeaderStrList(List<String> fieldList, Map<String, CSVFieldDTO> csvFieldDTOMap) {
        return fieldList.stream().map(t -> csvFieldDTOMap.get(t).getHeader()).collect(Collectors.toList());
    }

    private static List<List<String>> buildContentList(List<String> fieldList, Map<String, CSVFieldDTO> csvFieldDTOMap, List<?> dtoList) {
        return dtoList.stream().map(t -> buildContent(fieldList, csvFieldDTOMap, t)).collect(Collectors.toList());
    }

    private static <T> List<String> buildContent(List<String> fieldList, Map<String, CSVFieldDTO> csvFieldDTOMap, T model) {
        return fieldList.stream().map(t -> {
            CSVFieldDTO dto = csvFieldDTOMap.get(t);
            Method getMethod = dto.getGetMethod();
            Class<? extends AttributeStringConverter> converterClz = dto.getConverterClz();
            try {
                Object result = getMethod.invoke(model);
                return converterClz.getDeclaredConstructor().newInstance().process(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private static <T> Map<String, CSVFieldDTO> convert(Class<?> modelClz) throws NoSuchMethodException {

        Map<String, CSVFieldDTO> csvFieldDTOMap = new HashedMap();
        FieldOrder order = modelClz.getAnnotation(FieldOrder.class);
        List<String> orderList = CustomCollectionUtils.toList(order.value());
        for (int i = 0; i < orderList.size(); i++) {
            String fieldName = orderList.get(i);
            CSVFieldDTO dto = convertCSVFieldDTO(modelClz, fieldName);
            csvFieldDTOMap.put(fieldName, dto);
        }
        return csvFieldDTOMap;
    }

    private static CSVFieldDTO convertCSVFieldDTO(Class<?> modelClz, String fieldName) throws NoSuchMethodException {

        String methodName = CustomReflectionUtils.getMethodName(fieldName, KW_GET);
        Method getMethod = modelClz.getMethod(methodName);
        CSVField csvField = getMethod.getAnnotation(CSVField.class);
        String header = csvField.header();
        Class<? extends AttributeStringConverter> converterClz = csvField.converter();
        return new CSVFieldDTO(fieldName, header, getMethod, converterClz);
    }

    private static List<String> getFieldList(Class<?> modelClz) {

        FieldOrder order = modelClz.getAnnotation(FieldOrder.class);
        List<String> orderList = CustomCollectionUtils.toList(order.value());
        return orderList;
    }

    public static List<Map<String, String>> read(String srcFile) throws ServiceException {
        try {
            return FileUtils.readCSVFile(srcFile);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public static <T> List<T> read(String filePath, Class<T> clz) throws IOException {

        if (!new File(filePath).exists()) {
            return null;
        }

        List<T> modelList = new ArrayList<>();
        CsvReader reader = null;
        try {
            LockUtils.lock();
            reader = new CsvReader(filePath);

            reader.readHeaders();
            List<String> headerList = CustomCollectionUtils.toList(reader.getHeaders());

            List<List<String>> contentList = new ArrayList<>();
            while (reader.readRecord()) {
                List<String> fieldList = CustomCollectionUtils.toList(reader.getValues());
                if (fieldList.size() < headerList.size()) {
                    continue;
                }
                contentList.add(fieldList);
            }
            reader.close();
            LockUtils.unLock();

            contentList.forEach(fieldList -> {
                T model = EventUtils.deserialize(headerList, fieldList, clz);
                modelList.add(model);
            });
        } catch (Exception e) {
            if (reader != null) {
                reader.close();
            }
            LockUtils.unLock();
            logger.error(e.getMessage(), e);
            throw e;
        }
        return modelList;
    }

    public static <T> List<T> readCSV(String filePath, Class<T> clz) throws IOException {

        if (!new File(filePath).exists()) {
            return null;
        }

        List<T> modelList = new ArrayList<>();
        CsvReader reader = null;
        try {
            reader = new CsvReader(new FileInputStream(filePath), Charset.forName("GBK"));

            reader.readHeaders();
            List<String> headerList = CustomCollectionUtils.toList(reader.getHeaders());

            List<List<String>> contentList = new ArrayList<>();
            while (reader.readRecord()) {
                List<String> fieldList = CustomCollectionUtils.toList(reader.getValues());
                if (fieldList.size() < headerList.size()) {
                    continue;
                }
                contentList.add(fieldList);
            }
            reader.close();

            Map<String, String> fieldMap = new HashedMap();
            List<Field> fieldList = Arrays.asList(clz.getDeclaredFields()).stream().collect(Collectors.toList());
            fieldList.forEach(f -> {
                CSVField csvField = f.getAnnotation(CSVField.class);
                String header = csvField.header();
                fieldMap.put(header, f.getName());
            });

            List<Method> setMethodList = new ArrayList<>();
            headerList.forEach(t -> {
                String fieldName = fieldMap.get(t);
                String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    setMethodList.add(clz.getMethod(setMethodName, String.class));
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });

            contentList.forEach(c -> {
                List<String> list = c;
                try {
                    Object obj = clz.getConstructor(null).newInstance();
                    for (int i = 0; i < setMethodList.size(); i++) {
                        Method method = setMethodList.get(i);
                        method.invoke(obj, list.get(i));
                    }
                    modelList.add((T) obj);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            if (reader != null) {
                reader.close();
            }
            LockUtils.unLock();
            logger.error(e.getMessage(), e);
            throw e;
        }
        return modelList;
    }

}
