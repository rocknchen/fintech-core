package com.hthk.fintech.config;

import com.hthk.fintech.model.data.DataSourceTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${local.file.root}")
    private String localFileRootFolder;

    @Value("${app.version}")
    public String appVersion;

    @Value("${dataSource.type}")
    public DataSourceTypeEnum dataSourceType;

    @Value("${dataSource.path}")
    public String dataSourcePath;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${event.folder}")
    private String eventFolder;

    @Value("${logging.controller.exception.printStack:true}")
    private boolean controllerPrintStack;

    @Value("${tmpFolder:null}")
    private String tmpFolder;

    public String getTmpFolder() {
        return tmpFolder;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public boolean isControllerPrintStack() {
        return controllerPrintStack;
    }

    public String getDataSourcePath() {
        return dataSourcePath;
    }

    public DataSourceTypeEnum getDataSourceType() {
        return dataSourceType;
    }

    public String getLocalFileRootFolder() {
        return localFileRootFolder;
    }

    public String getEventFolder() {
        return eventFolder;
    }

    public String getAppName() {
        return appName;
    }
}
