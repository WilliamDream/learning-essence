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

    private String throwName;


    public MyThrowsAdviceInterceptor(Method method, Object aspectTarget) {
        super(method, aspectTarget);
    }

    @Override
    public Object invoke(MyMethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }

    }

    public void setThrowName(String name){
        this.throwName = name;
    }
}
