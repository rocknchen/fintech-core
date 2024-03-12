package com.hthk.fintech.service.impl;

import com.hthk.fintech.service.FTPService;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/11 14:44
 */
@Component
public class FTPServiceImpl implements FTPService {

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
