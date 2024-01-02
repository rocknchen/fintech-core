package com.hthk.fintech.model.web.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hthk.fintech.model.common.JsonSerializeConfig;

/**
 * @Author: Rock CHEN
 * @Date: 2024/1/2 11:09
 */
@JsonSerializeConfig(target = HttpRequestGetParams.class)
@JsonIgnoreProperties({"applicationSource"})
public interface HttpRequestGetParamsConfig {
}
