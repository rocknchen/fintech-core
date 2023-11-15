package com.hthk.fintech.utils;

import com.hthk.fintech.model.web.http.HttpResponse;
import com.hthk.fintech.model.web.http.HttpStatusCodeEnum;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 14:38
 */
public class HttpResponseUtils {

    public static HttpResponse success() {
        HttpResponse resp = new HttpResponse(HttpStatusCodeEnum.SUCCESS);
        return resp;
    }

    public static <T> HttpResponse success(T data) {
        HttpResponse resp = new HttpResponse(HttpStatusCodeEnum.SUCCESS);
        resp.setData(data);
        return resp;
    }

    public static HttpResponse fail() {
        HttpResponse resp = new HttpResponse(HttpStatusCodeEnum.FAIL);
        return resp;
    }

    public static <T> HttpResponse fail(T data) {
        HttpResponse resp = new HttpResponse(HttpStatusCodeEnum.FAIL);
        resp.setData(data);
        return resp;
    }

    public static HttpResponse internalError(String errMsg) {
        HttpResponse resp = new HttpResponse(HttpStatusCodeEnum.INTERNAL_ERROR);
        resp.setMessage(errMsg);
        return resp;
    }

}
