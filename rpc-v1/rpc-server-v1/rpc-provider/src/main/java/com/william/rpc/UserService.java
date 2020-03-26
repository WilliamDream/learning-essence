package com.william.rpc;


public class UserService implements IUserService{
    @Override
    public String getUserByName(String name) {
        System.out.println("服务端接收:"+ name);
        return "你好:"+ name;
    }

    @Override
    public void saveUser(String name,int age) {
        System.out.println("保存成功。");
    }
}
