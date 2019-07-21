package com.william.webdemo.service.impl;

import com.william.minispringmvc.annotation.MyService;
import com.william.webdemo.service.IUserService;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/7/21 13:15
 */

@MyService
public class UserServiceImpl implements IUserService{

    @Override
    public String addUser(String name, String account) {
        return "用户名："+name +" 用户账号："+account;
    }
}
