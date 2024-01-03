package com.hthk.fintech.service.impl;

import com.hthk.fintech.service.ExtLibsPomFileBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.LOG_DEFAULT;
import static com.hthk.fintech.config.FintechStaticData.LOG_WRAP;

/**
 * @Author: Rock CHEN
 * @Date: 2024/1/3 15:14
 */
public class ExtLibsPomFileBuilderImpl implements ExtLibsPomFileBuilder {

    private final static Logger logger = LoggerFactory.getLogger(ExtLibsPomFileBuilderImpl.class);

    @Override
    public String generate(String srcFolder, String ext) {

        List<String> fileNameList = Arrays.stream(new File(srcFolder).list()).collect(Collectors.toList());
        logger.info(LOG_WRAP, "fileNameList", fileNameList);

        String output = fileNameList.stream().map(this::buildOutputMsg).collect(Collectors.joining("\r\n"));
        return output;
    }

    private String buildOutputMsg(String name) {

        String pureName = name.substring(0, name.length() - 4);
        StringBuilder sb = new StringBuilder();
        sb.append("        <dependency>").append("\r\n");
        sb.append("            <groupId>calypso</groupId>").append("\r\n");
        sb.append("            <artifactId>" + pureName + "</artifactId>").append("\r\n");
        sb.append("            <version>1.0</version>").append("\r\n");
        sb.append("            <scope>system</scope>").append("\r\n");
        sb.append("            <systemPath>${project.basedir}/calypso_libs/" + name + "</systemPath>").append("\r\n");
        sb.append("        </dependency>").append("\r\n");
        return sb.toString();
    }

}
