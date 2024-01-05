package com.hthk.fintech.utils;

import com.hthk.common.exception.ServiceException;
import com.hthk.fintech.model.web.http.HttpStatusCodeEnum;

import java.util.Arrays;

/**
 * @Author: Rock CHEN
 * @Date: 2024/1/5 17:33
 */
public class HttpStatusCodeUtils {

    public static HttpStatusCodeEnum parse(int code) throws ServiceException {
        return Arrays.stream(HttpStatusCodeEnum.values()).filter(t -> t.getStatusCode() == code).findFirst().orElseThrow(() -> new ServiceException("no status code: " + code));
    }

}
