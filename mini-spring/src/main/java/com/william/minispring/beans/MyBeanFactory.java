package com.william.minispring.beans;

/**
 * @Author: WilliamDream
 * @Description: IOC顶层容器接口
 * @Date: 2019/9/22 15:57
 */
public interface MyBeanFactory {

    /**
     * 1.类不是延时加载直接获得初始化完的bean
     * 2.类是延时加载则先初始化
     * @param beanName
     * @return
     */
    Object getBean(String beanName)throws Exception;



}
