package com.hthk.common.utils;

import com.csvreader.CsvReader;
import com.hthk.common.exception.ServiceException;
import com.hthk.fintech.service.EntityFileService;
import com.hthk.fintech.structure.utils.JacksonUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_FILE_CHARSET_NAME;

public class FileUtils {

    public static LocalDate getFileDate(File file, String format
    ) {
        String fileName = file.getName();
        String simFileName = fileName.substring(0, fileName.lastIndexOf("."));
        String date = simFileName.substring(simFileName.length() - format.length());
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }

    public static File copy(
            File srcFile,
            String deskFolder,
            boolean force
    ) throws FileNotFoundException {
        String fileName = srcFile.getName();
        if (force) {
            new File(deskFolder).mkdirs();
        }
        File deskFile = new File(deskFolder + "/" + fileName);
        try {
            FileCopyUtils.copy(srcFile, deskFile);
            return deskFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static File build(
            String filePath,
            String content,
            boolean force
    ) {
        try {
            File folder = new File(filePath).getParentFile();
            if (!folder.exists() && force) {
                folder.mkdirs();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File(filePath);
    }

    public static File build(
            String filePath,
            String content,
            String charsetName,
            boolean force
    ) {
        try {
            File folder = new File(filePath).getParentFile();
            if (!folder.exists() && force) {
                folder.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(filePath);
            Writer out = new OutputStreamWriter(fos, charsetName);
            out.write(content);
            out.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File(filePath);
    }

    public static String readResourceAsStr(
            File file,
            boolean wrap
    ) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = reader.lines().collect(Collectors.toList());
            reader.close();

            String delimiter = wrap ? "\n" : "";
            String fileContent = String.join(delimiter, lines);
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readClzPathResourceList(String dictFolder) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource(dictFolder);
        try {
            List<String> fileList = getAllSubFileList(resource.getFile());
            return fileList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getAllSubFileList(File file) {
        List<String> subFileList = new ArrayList<>();
        if (file.isDirectory()) {
            subFileList.addAll(Arrays.stream(file.listFiles()).map(t -> getAllSubFileList(t))
                    .flatMap(Collection::stream).collect(Collectors.toList()));
        } else {
            subFileList.add(file.getAbsolutePath());
        }
        return subFileList;
    }

    public static List<String> readResourceAsStrList(
            File file
    ) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = reader.lines().collect(Collectors.toList());
            reader.close();
            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readClassPathResourceAsStr(
            String classPathResource,
            boolean wrap
    ) {

        try {

            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource(classPathResource);

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(resource.getInputStream(), DEFAULT_FILE_CHARSET_NAME));
            List<String> lines = reader.lines().collect(Collectors.toList());
            reader.close();

            String delimiter = wrap ? "\n" : "";
            String fileContent = String.join(delimiter, lines);
            return fileContent;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String readClassPathResourceAsStr(String classPathResource) {
        return readClassPathResourceAsStr(classPathResource, false);
    }

    public static List<Map<String, String>> readCSVFile(String srcFile) throws IOException {

        List<Map<String, String>> tradeInfoList = new ArrayList<>();
        List<String[]> csvList = new ArrayList<>();

        CsvReader reader = new CsvReader(srcFile, ',', Charset.forName("UTF-8"));
        reader.readHeaders();
        String[] headers = reader.getHeaders();
        List<String> headerStrList = Arrays.stream(headers).collect(Collectors.toList());
        while (reader.readRecord()) {
            csvList.add(reader.getValues());
        }
        reader.close();

        csvList.forEach(
                line -> {
                    List<String> oneLineList = Arrays.stream(line).collect(Collectors.toList());
                    Map<String, String> map = new HashMap<>();
                    for (int i = 0; i < headerStrList.size(); i++) {
                        map.put(headerStrList.get(i), oneLineList.get(i));
                    }
                    tradeInfoList.add(map);
                }
        );

        return tradeInfoList;
    }

    public static <R> List<R> getEntityList(String srcFolder, Class<R> clz) throws ServiceException {

        File[] files = new File(srcFolder).listFiles();
        if (files == null) {
            return null;
        }
        List<File> srcFileList = Arrays.stream(files).collect(Collectors.toList());
        List<R> resultList = new ArrayList<>();
        try {
            for (File file : srcFileList) {
                R entity = readEntity(file, clz);
                resultList.add(entity);
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return resultList;
    }

    public static <R> R readEntity(File srcFile, Class<R> clz) throws IOException, ServiceException {

        String yml = FileUtils.readResourceAsStr(srcFile, true);
        return JacksonUtils.readYml(yml, clz);
    }

    public static <T> void writeOutput(List<T> tradeList, String outputFolder, EntityFileService nameService) {

        tradeList.forEach(t -> {
            String fileName = nameService.getFileName(t);
            String filePath = outputFolder + "/" + fileName;
            String json = JacksonUtils.toJsonPrettyTry(t);
            FileUtils.build(filePath, json, true);
        });
    }

    public static List<String> getAllSubFolderList(File file) {
        List<String> subFolderList = new ArrayList<>();
        if (file.isDirectory()) {
            subFolderList.add(file.getAbsolutePath());
            subFolderList.addAll(Arrays.stream(file.listFiles()).map(t -> getAllSubFolderList(t))
                    .flatMap(Collection::stream).collect(Collectors.toList()));
        }
        return subFolderList;
    }

}
