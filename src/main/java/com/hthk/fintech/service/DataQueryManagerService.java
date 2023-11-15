package com.hthk.fintech.service;

import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;
import com.hthk.fintech.model.web.http.HttpRequest;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 21:52
 */
public interface DataQueryManagerService {

    <R> R get(HttpRequest<? extends IDataCriteria> request);

}
