package com.william.rpc;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/5/26 10:39
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    protected UserServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String showUserName(String name) throws RemoteException{
        return "Wellcome :"+name;
    }
}
