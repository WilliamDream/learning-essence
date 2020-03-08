package com.william.minispring.framework.beans.support;

import com.william.minispring.framework.beans.MyBeanFactory;
import com.william.minispring.framework.beans.config.MyBeanDefinition;
import com.william.minispring.framework.context.support.MyAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/22 16:09
 */
public class MyDefaultListableBeanFactory extends MyAbstractApplicationContext implements MyBeanFactory {

    protected final Map<String, MyBeanDefinition> beanDefinitionMap = new ConcurrentHashMap(256);


    @Override
    public Object getBean(String beanName)throws Exception {
        return null;
    }
}
