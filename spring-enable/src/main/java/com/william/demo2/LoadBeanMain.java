package com.william.demo2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/15 15:36
 */
public class LoadBeanMain {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

        String [] beans = context.getBeanDefinitionNames();
        for(int i=0;i<beans.length;i++){
            System.out.println(beans[i]);
        }



    }

}
