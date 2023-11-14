package com.hthk.fintech.service.impl;

import com.hthk.fintech.model.software.app.AppVersion;
import com.hthk.fintech.service.AppInfoService;
import com.hthk.fintech.service.basic.AbstractService;
import org.springframework.stereotype.Service;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 16:24
 */
@Service("basicAppInfoService")
public class BasicAppInfoService extends AbstractService implements AppInfoService {
    @Override
    public AppVersion getVersion() {
        return new AppVersion(appConfig.appVersion);
    }

}
