package com.hthk.fintech.service.impl;

import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;
import com.hthk.fintech.model.web.http.HttpRequest;
import com.hthk.fintech.service.DataQueryManagerService;
import org.springframework.stereotype.Service;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 21:53
 */
@Service
public class DataQueryServiceImpl implements DataQueryManagerService {

    @Override
    public <R> R get(HttpRequest<? extends IDataCriteria> request) {
        return null;
    }

}
