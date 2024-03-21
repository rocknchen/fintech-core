package com.hthk.fintech.service;

import com.hthk.fintech.exception.ServiceInternalException;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/21 18:11
 */
public interface SFTPService {

    Session connect(String serverIP, Integer port, String user, String password) throws IOException, ServiceInternalException, JSchException;

}
