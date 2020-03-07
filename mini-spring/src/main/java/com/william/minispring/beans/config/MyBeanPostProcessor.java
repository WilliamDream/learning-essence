package com.william.minispring.beans.config;

/**
 * @Auther: williamdream
 * @Date: 2019/9/26 20:27
 * @Description:
 */
public class MyBeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception{
        return null;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception{
        return null;
    }
}
