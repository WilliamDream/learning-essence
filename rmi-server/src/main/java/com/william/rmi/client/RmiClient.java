package com.william.rmi.client;

import com.william.rmi.server.UserService;
import com.william.rmi.server.UserServiceImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/5/26 10:54
 */
public class RmiClient {

    public static void main(String[] args) throws RemoteException,MalformedURLException,NotBoundException {
        UserService userService = null;
        userService = (UserService) Naming.lookup("rmi://127.0.0.1/userservice");
        System.out.println(userService.showUserName("William"));

    }
}
