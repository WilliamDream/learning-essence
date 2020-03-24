package com.william.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/5/26 10:36
 */
public interface UserService  extends Remote{

    String showUserName(String name)throws RemoteException;

}
