package com.william.rpc;


public interface IUserService {

    String getUserByName(String name);

    void saveUser(String name,int age);

}
