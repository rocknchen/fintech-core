package com.hthk.fintech.service.impl;

import com.hthk.fintech.enumration.FTPTypeEnum;
import com.hthk.fintech.exception.ServiceInternalException;
import com.hthk.fintech.model.net.ftp.FTPConnection;
import com.hthk.fintech.model.net.ftp.FTPSource;
import com.hthk.fintech.service.FTPClientService;
import com.hthk.fintech.service.SFTPService;
import com.hthk.fintech.service.basic.AbstractConnectionService;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Vector;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.FintechStaticData.LOG_DEFAULT;
import static com.hthk.fintech.config.FintechStaticData.LOG_WRAP;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/21 18:14
 */
@Component("sftpService")
public class SFTPServiceImpl

        extends AbstractConnectionService

        implements SFTPService, FTPClientService {

    private final static Logger logger = LoggerFactory.getLogger(SFTPServiceImpl.class);

    @Override
    public void before(FTPConnection connection) throws SftpException, JSchException {
        Session jSchSession = connection.getSession();
        ChannelSftp chSftp = (ChannelSftp) jSchSession.openChannel("sftp");
        chSftp.connect();
        chSftp.setFilenameEncoding("UTF-8");
        connection.setChSftp(chSftp);
    }

    @Override
    public void after(FTPConnection connection) {
        ChannelSftp chSftp = connection.getChSftp();
        chSftp.disconnect();
    }

    @Override
    public void upload(FTPConnection connection, String folder, String fileInTmp) throws ServiceInternalException {

        String remoteFileName = new File(fileInTmp).getName();
        ChannelSftp chSftp = connection.getChSftp();

        try {
            chSftp.cd(folder);
            FileInputStream fis = new FileInputStream(fileInTmp);
            chSftp.put(fis, remoteFileName);
            fis.close();
        } catch (Exception e) {
            throw new ServiceInternalException(e.getMessage(), e);
        }
    }

    @Override
    public String download(FTPConnection connection, String folder, String name, String tmpFolder) throws ServiceInternalException {

        String remoteFile = folder + "/" + name;
        String localFile = tmpFolder + "/" + name;
        ChannelSftp chSftp = connection.getChSftp();

        try {
            chSftp.get(remoteFile, localFile);
            return localFile;
        } catch (Exception e) {
            throw new ServiceInternalException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> list(FTPConnection connection, String changeFolder) throws ServiceInternalException {

        try {
            ChannelSftp chSftp = connection.getChSftp();
            Vector vector = chSftp.ls(changeFolder);
            List<String> fileName = getNameList(vector);
            return fileName;
        } catch (Exception e) {
            throw new ServiceInternalException(e.getMessage(), e);
        }
    }

    private List<String> getNameList(Vector vector) {
        List<ChannelSftp.LsEntry> entryList = (List<ChannelSftp.LsEntry>) vector.stream().collect(Collectors.toList());
        List<ChannelSftp.LsEntry> fileList = entryList.stream().filter(t -> !t.getAttrs().isDir()).collect(Collectors.toList());
        return fileList.stream().map(t -> t.getFilename()).collect(Collectors.toList());
    }

    @Override
    public FTPConnection connect(FTPSource ftpSource) throws IOException, ServiceInternalException, JSchException {

        int port = Optional.ofNullable(ftpSource.getPort()).orElse(22);
        Session session = connect(ftpSource.getServer(), port, ftpSource.getUser(), ftpSource.getPwd());
        FTPConnection conn = new FTPConnection(FTPTypeEnum.SFTP);
        conn.setId(ftpSource.getId());
        conn.setSession(session);
        return conn;
    }

    @Override
    public Session connect(String serverIP, Integer port, String user, String password) throws IOException, ServiceInternalException, JSchException {

        JSch jSch = new JSch();
        Session jSchSession;
        jSchSession = jSch.getSession(user, serverIP, port);
        jSchSession.setPassword(password);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        jSchSession.setConfig(config);

        jSchSession.setTimeout(3000);

        try {
            jSchSession.connect();
        } catch (JSchException e) {
            log(serverIP, false);
            throw e;
        }

        boolean isSuccess = jSchSession.isConnected();
        log(serverIP, isSuccess);
        exp(isSuccess);

        return jSchSession;
    }

}
