package com.william.demo3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/15 22:11
 */

@Configuration
@EnableMilk(type = Milk.Type.MENGNIU,name = "蒙牛")
public class EnableDemoMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(EnableDemoMain.class);
        context.refresh();
        Milk milk = context.getBean(Milk.class);
        milk.processMilk();
        milk.getMilk();


    }

}
