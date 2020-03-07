package com.william.minispring;

import com.william.minispring.context.MyApplicationContext;

/**
 * @Auther: williamdream
 * @Date: 2019/10/10 11:56
 * @Description:
 */
public class MySpringTest {

    public static void main(String[] args) {
        MyApplicationContext context = new MyApplicationContext("classpath:application.properties");
        System.out.print(context);

    }

}
