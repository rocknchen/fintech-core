package com.hthk.common.utils;

import com.csvreader.CsvReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_FILE_CHARSET_NAME;

public class FileUtils {

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

}