package com.william.webdemo.controller;

import com.william.minispringmvc.annotation.MyAutoWired;
import com.william.minispringmvc.annotation.MyController;
import com.william.minispringmvc.annotation.MyRequestMapping;
import com.william.minispringmvc.annotation.MyRequestParam;
import com.william.webdemo.service.IUserService;

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
    public String addUser(@MyRequestParam("n") String name,@MyRequestParam("a") String account){
        return userService.addUser(name,account);

    }
    
}
