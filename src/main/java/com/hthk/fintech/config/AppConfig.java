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

    @Value("${spring.application.name}")
    public String appName;

    @Value("${event.folder}")
    private String eventFolder;

    public String getLocalFileRootFolder() {
        return localFileRootFolder;
    }

    public String getEventFolder() {
        return eventFolder;
    }
}
