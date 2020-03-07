package com.william.minispring.beans;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/25 21:45
 */
public class MyBeanWrapper {

    private Object wrappedInstance;

    private Class<?> wrappedClass;

    public MyBeanWrapper() {
    }

    public MyBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }


    public Object getWrappedInstance(){
        return this.wrappedInstance;
    }

    public Class<?> getWrappedClass(){
        return this.wrappedClass;
    }
}
