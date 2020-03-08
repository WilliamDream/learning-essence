package com.william.minispring.framework.aop.intercept;

/**
 * @Auther: williamdream
 * @Date: 2019/10/11 17:20
 * @Description:
 */
public interface MyMethodInterceptor {


    Object invoke(MyMethodInvocation invocation) throws Throwable;

}
