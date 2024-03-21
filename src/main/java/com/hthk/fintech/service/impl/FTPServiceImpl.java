package com.hthk.fintech.service.impl;

import com.hthk.fintech.enumration.FTPTypeEnum;
import com.hthk.fintech.exception.ServiceInternalException;
import com.hthk.fintech.model.net.ftp.FTPConnection;
import com.hthk.fintech.model.net.ftp.FTPSource;
import com.hthk.fintech.service.FTPClientService;
import com.hthk.fintech.service.FTPService;
import com.hthk.fintech.service.basic.AbstractConnectionService;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/11 14:44
 */
@Component("ftpService")
public class FTPServiceImpl

        extends AbstractConnectionService

        implements FTPService, FTPClientService {

    private final static Logger logger = LoggerFactory.getLogger(FTPServiceImpl.class);

    @Override
    public FTPConnection connect(FTPSource ftpSource) throws IOException, ServiceInternalException {

        int port = Optional.ofNullable(ftpSource.getPort()).orElse(21);
        FTPClient ftpClient = connect(ftpSource.getServer(), port, ftpSource.getUser(), ftpSource.getPwd());
        FTPConnection conn = new FTPConnection(FTPTypeEnum.FTP);
        conn.setId(ftpSource.getId());
        conn.setFtpClient(ftpClient);
        return conn;
    }

    @Override
    public FTPClient connect(String serverIP, Integer port, String user, String password) throws IOException, ServiceInternalException {

        FTPClient client = new FTPClient();
        client.connect(serverIP, 21);
        boolean isSuccess = client.login(user, password);
        log(serverIP, isSuccess);
        exp(isSuccess);
        client.setFileType(FTPClient.BINARY_FILE_TYPE);
        return client;
    }

    @Override
    public void send(String filePath, String remoteFolder, String serverIP, String user, String password) throws IOException {

        FTPClient client = new FTPClient();
        client.connect(serverIP, 21);
        client.login(user, password);

        client.setFileType(FTPClient.BINARY_FILE_TYPE);

        client.changeWorkingDirectory(remoteFolder);

        FileInputStream fis = new FileInputStream(filePath);
        client.storeFile(new File(filePath).getName(), fis);
        fis.close();

        client.logout();
        client.disconnect();

    }

}