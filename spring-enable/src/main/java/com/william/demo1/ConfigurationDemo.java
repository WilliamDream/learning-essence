package com.william.demo1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/15 15:02
 */

@ComponentScan(basePackages = "com.william.demo1")
public class ConfigurationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationDemo.class);

        String [] beans = context.getBeanDefinitionNames();
        for(int i=0;i<beans.length;i++){
            System.out.println(beans[i]);
        }


    }

}
