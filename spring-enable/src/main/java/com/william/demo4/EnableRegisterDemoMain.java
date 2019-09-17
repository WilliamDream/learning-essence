package com.william.demo4;

import com.william.demo3.Milk;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/15 22:25
 */

@Configuration
@EnableNewMilk(type = Milk.Type.YILI,name = "伊利")
public class EnableRegisterDemoMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(EnableRegisterDemoMain.class);
        context.refresh();
        Milk milk = context.getBean(Milk.class);
        milk.processMilk();
        milk.getMilk();


    }

}
