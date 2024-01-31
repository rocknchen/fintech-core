package com.hthk.fintech.service;

import com.hthk.fintech.exception.ServiceInternalException;
import com.hthk.fintech.exception.ServiceNotSupportedException;
import com.hthk.fintech.model.web.http.HttpServiceRequest;

/**
 * @Author: Rock CHEN
 * @Date: 2024/1/26 14:08
 */
public interface ServiceManager {

    <R, P, C> R process(HttpServiceRequest<P, C> request) throws ServiceNotSupportedException, ServiceInternalException;

}
