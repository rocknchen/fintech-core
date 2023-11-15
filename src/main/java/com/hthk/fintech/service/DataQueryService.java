package com.hthk.fintech.service;

import com.hthk.fintech.model.data.datacenter.query.DataSnapshot;
import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/15 17:11
 */
public interface DataQueryService<T extends IDataCriteria> {

    <R> R get(DataSnapshot snapshot, T criteria);

}
