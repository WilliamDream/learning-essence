package com.william.demo5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/17 21:04
 */
@EnableDataSource
public class EnableDatasourceMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册当前引导类（被@Configuration注解标注）到Spring上下文中
        context.register(EnableDatasourceMain.class);
        context.refresh();
        //获取名为dataSource的bean对象
        Object object = context.getBean("dataSource");
        System.out.println(object);
        context.close();
    }
}
