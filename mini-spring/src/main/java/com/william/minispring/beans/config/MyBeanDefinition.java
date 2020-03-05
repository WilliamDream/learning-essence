package com.william.minispring.beans.config;

/**
 * @Author: WilliamDream
 * @Description: Spring中BeanDefinition是接口，他的实现类如XmlBeanDefinition、AnnotationBeanDefiniton等
 * 这里直接写为普通类
 * @Date: 2019/9/22 16:15
 */
public class MyBeanDefinition {

    private String beanClassName;

    private boolean lazyInit = false;

    private String factoryBeanName;

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }
}
