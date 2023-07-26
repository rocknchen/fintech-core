package com.hthk.fintech.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${local.file.root}")
    private String localFileRootFolder;

    public String getLocalFileRootFolder() {
        return localFileRootFolder;
    }

}
