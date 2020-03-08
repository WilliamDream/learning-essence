package com.william.minispring.webdemo.controller;


import com.william.minispring.framework.annotation.MyAutoWired;
import com.william.minispring.framework.annotation.MyController;
import com.william.minispring.framework.annotation.MyRequestMapping;
import com.william.minispring.framework.annotation.MyRequestParam;
import com.william.minispring.webdemo.service.IUserService;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/7/21 13:14
 */

@MyController
@MyRequestMapping("/user")
public class UserController {

    @MyAutoWired
    private IUserService userService;

    @MyRequestMapping("/addUser")
    public String addUser(@MyRequestParam("n") String name, @MyRequestParam("a") String account){
        return userService.addUser(name,account);

    }

}
