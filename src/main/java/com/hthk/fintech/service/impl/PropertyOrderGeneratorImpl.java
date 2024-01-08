package com.hthk.fintech.service.impl;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hthk.common.utils.FileUtils;
import com.hthk.fintech.service.PropertyOrderGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.LOG_DEFAULT;
import static com.hthk.fintech.config.FintechStaticData.LOG_WRAP;

/**
 * @Author: Rock CHEN
 * @Date: 2024/1/8 15:02
 */
@Component
public class PropertyOrderGeneratorImpl implements PropertyOrderGenerator {

    private final static Logger logger = LoggerFactory.getLogger(PropertyOrderGeneratorImpl.class);

    @Override
    public String process(String nameSpace, String sourceFile, int offset) {

        List<String> nameList = getNameList(sourceFile);
        String content = buildContent(nameList, offset);
        return buildResult(nameSpace, content);
    }

    private String buildResult(String nameSpace, String content) {

        StringBuilder sb = new StringBuilder("@");
        sb.append(nameSpace).append("({").append(content).append("})");
        return sb.toString();
    }

    private String buildContent(List<String> nameList, int offset) {

        StringBuilder sb = new StringBuilder("\"");
        int temp = 1;
        for (String name : nameList) {
            if (temp++ % offset == 0) {
                sb.append(name).append("\", \r\n\"");
            } else {
                sb.append(name).append("\", \"");
            }
        }
        sb.delete(sb.length() - 3, sb.length());
        return sb.toString();
    }

    private List<String> getNameList(String sourceFile) {

        List<String> strList = FileUtils.readResourceAsStrList(new File(sourceFile));
        List<String> validStrList = strList.stream().filter(t -> t.trim().startsWith("private String"))
                .map(t -> t.trim().substring("private String".length() + 1, t.trim().length() - 1)).collect(Collectors.toList());
        return validStrList;
    }

}
