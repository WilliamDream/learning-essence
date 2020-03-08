package com.william.minispring.framework.aop;

import com.william.minispring.framework.aop.intercept.MyMethodInvocation;
import com.william.minispring.framework.aop.support.MyAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 /**
 * @Auther: williamdream
 * @Date: 2010/10/11 15:06
 * @Description:
 */
public class MyJdkDynamicAopProxy implements MyAopProxy, InvocationHandler {

    public MyAdvisedSupport advised;

    public MyJdkDynamicAopProxy(MyAdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {

        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method,this.advised.getTargetClass());
        MyMethodInvocation invocation = new MyMethodInvocation(proxy,null,method,args,this.advised.getTargetClass(),chain);
        return invocation.proceed();
    }
}
