package com.william.minispring.framework.aop.intercept;

/**
 * @Auther: williamdream
 * @Date: 2019/10/11 17:20
 * @Description: 将调用的方法封装为拦截器链
 */
public interface MyMethodInterceptor {


    Object invoke(MyMethodInvocation invocation) throws Throwable;

}
