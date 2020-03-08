package com.william.minispring.framework.aop;

/**
 * @Auther: williamdream
 * @Date: 2010/10/11 15:05
 * @Description:
 */
public interface MyAopProxy {


    Object getProxy();

    Object getProxy(ClassLoader classLoader);

}
