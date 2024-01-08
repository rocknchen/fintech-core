package com.hthk.fintech.service;

/**
 * @Author: Rock CHEN
 * @Date: 2024/1/8 15:01
 */
public interface PropertyOrderGenerator {

    String process(String nameSpace, String sourceFile, int offset);
}
