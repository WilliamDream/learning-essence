package com.william.rpc;


public class UserService implements IUserService{
    @Override
    public String getUserByName(String name) {
        return "返回User:"+ name;
    }

    @Override
    public void saveUser(String name,int age) {
        System.out.println("保存成功。");
    }
}
