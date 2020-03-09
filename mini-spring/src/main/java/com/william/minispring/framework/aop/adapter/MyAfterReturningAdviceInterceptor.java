package com.william.minispring.framework.aop.adapter;

import com.william.minispring.framework.aop.intercept.MyMethodInterceptor;
import com.william.minispring.framework.aop.intercept.MyMethodInvocation;

import java.lang.reflect.Method;

/**
 * @Auther: williamdream
 * @Date: 2019/10/12 22:24
 * @Description:
 */
public class MyAfterReturningAdviceInterceptor extends MyAbstractAspectAdvice implements MyMethodInterceptor {

    public MyAfterReturningAdviceInterceptor(Method method, Object aspectTarget) {
        super(method, aspectTarget);
    }

    @Override
    public Object invoke(MyMethodInvocation invocation) throws Throwable {
        return null;
    }
}
