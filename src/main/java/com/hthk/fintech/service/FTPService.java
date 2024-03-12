package com.hthk.fintech.service;

import java.io.IOException;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/11 14:41
 */
public interface FTPService {

    void send(String filePath, String remoteFolder, String serverIP, String user, String password) throws IOException;

}
