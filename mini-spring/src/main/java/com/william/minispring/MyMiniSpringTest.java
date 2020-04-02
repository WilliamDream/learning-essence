package com.william.minispring;


import com.william.minispring.framework.context.MyApplicationContext;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2020/1/18 11:06
 */
public class MyMiniSpringTest {

    public static void main(String[] args) {

        MyApplicationContext context = new MyApplicationContext("classpath:application.properties");
        System.out.println(context);

    }



}
