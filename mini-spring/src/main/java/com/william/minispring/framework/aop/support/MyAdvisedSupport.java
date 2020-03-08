package com.william.minispring.framework.aop.support;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Auther: williamdream
 * @Date: 2010/10/11 15:13
 * @Description:
 */
public class MyAdvisedSupport {

    //把符合切面表达式的类扫描出来
    public Class<?> getTargetClass(){
        return null;
    }

    public Object getTarget(){
        return null;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, @Nullable Class<?> targetClass) {

        return null;
    }
}
