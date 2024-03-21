package com.hthk.fintech.service;

import com.hthk.fintech.exception.ServiceInternalException;
import com.hthk.fintech.model.net.ftp.FTPConnection;
import com.hthk.fintech.model.net.ftp.FTPSource;
import com.jcraft.jsch.JSchException;

import java.io.IOException;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/21 17:37
 */
public interface FTPClientService {

    FTPConnection connect(FTPSource ftpSource) throws IOException, ServiceInternalException, JSchException;

}