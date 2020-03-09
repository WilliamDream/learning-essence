package com.william.minispring.framework.aop.adapter;

import com.william.minispring.framework.aop.intercept.MyMethodInterceptor;
import com.william.minispring.framework.aop.intercept.MyMethodInvocation;

import java.lang.reflect.Method;

/**
 * @Auther: williamdream
 * @Date: 2019/10/12 22:26
 * @Description:
 */
public class MyThrowsAdviceInterceptor extends MyAbstractAspectAdvice implements MyMethodInterceptor {

    public MyThrowsAdviceInterceptor(Method method, Object aspectTarget) {
        super(method, aspectTarget);
    }

    @Override
    public Object invoke(MyMethodInvocation invocation) throws Throwable {
        return null;
    }

    public void setThrowName(String name){

    }
}
