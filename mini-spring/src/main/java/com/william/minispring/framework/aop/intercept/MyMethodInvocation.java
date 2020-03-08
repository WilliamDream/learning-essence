package com.william.minispring.framework.aop.intercept;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Auther: williamdream
 * @Date: 2010/10/11 15:31
 * @Description:
 */
public class MyMethodInvocation {

    public MyMethodInvocation(
            Object proxy,  Object target, Method method,  Object[] arguments,
            Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {

//        this.proxy = proxy;
//        this.target = target;
//        this.targetClass = targetClass;
//        this.method = BridgeMethodResolver.findBridgedMethod(method);
//        this.arguments = AopProxyUtils.adaptArgumentsIfNecessary(method, arguments);
//        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public Object proceed() throws Throwable {
        return null;
    }
}
