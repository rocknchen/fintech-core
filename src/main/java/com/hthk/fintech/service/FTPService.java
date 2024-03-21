package com.hthk.fintech.service;

import com.hthk.fintech.exception.ServiceInternalException;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/11 14:41
 */
public interface FTPService {

    FTPClient connect(String serverIP, Integer port, String user, String password) throws IOException, ServiceInternalException;

    void send(String filePath, String remoteFolder, String serverIP, String user, String password) throws IOException;

}
