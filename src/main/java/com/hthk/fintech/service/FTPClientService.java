package com.hthk.fintech.service;

import com.hthk.fintech.exception.ServiceInternalException;
import com.hthk.fintech.model.net.ftp.FTPConnection;
import com.hthk.fintech.model.net.ftp.FTPSource;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.IOException;
import java.util.List;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/21 17:37
 */
public interface FTPClientService {

    FTPConnection connect(FTPSource ftpSource) throws IOException, ServiceInternalException, JSchException;

    List<String> list(FTPConnection connection, String changeFolder) throws ServiceInternalException, IOException;

    default void before(FTPConnection connection) throws SftpException, JSchException {
    }

    default void after(FTPConnection connection) throws IOException {
    }

    default void move(FTPConnection connection, String remoteSource, String remoteDestFolder) throws IOException {
    }

    String download(FTPConnection connection, String folder, String name, String tmpFolder) throws IOException, ServiceInternalException;

    void upload(FTPConnection connection, String folder, String fileInTmp) throws IOException, ServiceInternalException;
}
