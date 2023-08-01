package com.hthk.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ClassPathFileUtils {

    protected final static Logger logger = LoggerFactory.getLogger(ClassPathFileUtils.class);

    public static List<File> get(String clzPath) {

        URL clzPathResource = ClassLoader.getSystemResource(clzPath);
        String absPath = clzPathResource.getPath();
        List<File> fileList = Arrays.asList(new File(absPath).listFiles());
        return fileList;
    }

    public static String readFileAsStr(
            String classPathResource,
            boolean wrap
    ) {
        try {
            ClassPathResource pathResource = new ClassPathResource(classPathResource);
            InputStream is = pathResource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                if (wrap) {
                    sb.append("\r\n");
                }
            }
            is.close();
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFileAsStr(String classPathResource) {
        return readFileAsStr(classPathResource, false);
    }


}
