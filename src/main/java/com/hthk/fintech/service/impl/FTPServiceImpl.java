package com.hthk.fintech.service.impl;

import com.hthk.fintech.enumration.FTPTypeEnum;
import com.hthk.fintech.exception.ServiceInternalException;
import com.hthk.fintech.model.net.ftp.FTPConnection;
import com.hthk.fintech.model.net.ftp.FTPSource;
import com.hthk.fintech.service.FTPClientService;
import com.hthk.fintech.service.FTPService;
import com.hthk.fintech.service.basic.AbstractConnectionService;
import com.jcraft.jsch.Session;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void after(FTPConnection connection) throws IOException {

        FTPClient client = connection.getFtpClient();

        client.logout();
        client.disconnect();
    }

    @Override
    public void move(FTPConnection connection, String remoteSource, String remoteDestFolder) throws IOException {

        FTPClient client = connection.getFtpClient();
        String fileName = new File(remoteSource).getName();
        String remoteDest = remoteDestFolder + "/" + fileName;

        client.rename(remoteSource, remoteDest);
    }

    @Override
    public void upload(FTPConnection connection, String folder, String fileInTmp) throws IOException {

        FTPClient client = connection.getFtpClient();

        String remoteFile = folder + "/" + new File(fileInTmp).getName();

        FileInputStream fis = new FileInputStream(fileInTmp);
        client.storeFile(new File(remoteFile).getName(), fis);
        fis.close();
    }

    @Override
    public String download(FTPConnection connection, String folder, String name, String tmpFolder) throws IOException {

        FTPClient client = connection.getFtpClient();

        String remoteFile = folder + "/" + name;
        String localFile = tmpFolder + "/" + name;

        FileOutputStream fos = new FileOutputStream(localFile);
        client.retrieveFile(remoteFile, fos);
        fos.close();

        return localFile;
    }

    @Override
    public List<String> list(FTPConnection connection, String changeFolder) throws IOException {

        FTPClient client = connection.getFtpClient();
        client.changeWorkingDirectory(changeFolder);
        return Arrays.stream(client.listFiles()).filter(t -> t.isFile())
                .collect(Collectors.toList()).stream()
                .map(t -> t.getName()).collect(Collectors.toList());
    }

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
