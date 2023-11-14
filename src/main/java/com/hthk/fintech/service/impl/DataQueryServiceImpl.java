package com.hthk.fintech.service.impl;

import com.hthk.fintech.model.web.http.HttpRequest;
import com.hthk.fintech.service.DataQueryService;
import org.springframework.stereotype.Service;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 21:53
 */
@Service
public class DataQueryServiceImpl<R> implements DataQueryService {

    @Override
    public R get(HttpRequest request) {
        return null;
    }

}
