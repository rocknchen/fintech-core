package com.hthk.fintech.service.impl;

import com.hthk.fintech.enumration.FTPTypeEnum;
import com.hthk.fintech.exception.ServiceInternalException;
import com.hthk.fintech.model.net.ftp.FTPConnection;
import com.hthk.fintech.model.net.ftp.FTPSource;
import com.hthk.fintech.service.FTPClientService;
import com.hthk.fintech.service.SFTPService;
import com.hthk.fintech.service.basic.AbstractConnectionService;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/21 18:14
 */
@Component("sftpService")
public class SFTPServiceImpl

        extends AbstractConnectionService

        implements SFTPService, FTPClientService {

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
