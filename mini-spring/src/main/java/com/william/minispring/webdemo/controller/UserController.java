package com.william.minispring.webdemo.controller;


import com.william.minispring.framework.annotation.MyAutoWired;
import com.william.minispring.framework.annotation.MyController;
import com.william.minispring.framework.annotation.MyRequestMapping;
import com.william.minispring.framework.annotation.MyRequestParam;
import com.william.minispring.framework.webmvc.servlet.MyModelAndView;
import com.william.minispring.webdemo.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
    public MyModelAndView addUser(HttpServletRequest request, HttpServletResponse response, @MyRequestParam("n") String name, @MyRequestParam("a") String account){
        try {
            response.getWriter().write(userService.addUser(name,account));
        } catch (Exception e) {
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("detail",e.getMessage());
            model.put("stackTrace",e.getStackTrace());
            return new MyModelAndView("500",model);
        }
        return null;
    }

}
