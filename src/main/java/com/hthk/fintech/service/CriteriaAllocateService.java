package com.hthk.fintech.service;

import com.hthk.fintech.model.software.app.ApplicationEnum;
import com.hthk.fintech.model.web.http.ActionTypeEnum;
import com.hthk.fintech.model.web.http.RequestEntity;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/22 17:30
 */
public interface CriteriaAllocateService {

    Class getClz(ActionTypeEnum action, RequestEntity requestEntity, ApplicationEnum app);

}
