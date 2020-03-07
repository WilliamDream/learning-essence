package com.william.minispring.beans.support;

import com.william.minispring.beans.MyBeanFactory;
import com.william.minispring.beans.config.MyBeanDefinition;
import com.william.minispring.context.support.MyAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/22 16:09
 */
public class MyDefaultListableBeanFactory extends MyAbstractApplicationContext implements MyBeanFactory{

    protected final Map<String, MyBeanDefinition> beanDefinitionMap = new ConcurrentHashMap(256);


    @Override
    public Object getBean(String beanName)throws Exception {
        return null;
    }
}
