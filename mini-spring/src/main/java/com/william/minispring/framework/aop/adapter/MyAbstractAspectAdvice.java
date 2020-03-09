package com.william.minispring.framework.aop.adapter;

import java.lang.reflect.Method;

/**
 * @Auther: williamdream
 * @Date: 2019/10/12 22:32
 * @Description:
 */
public abstract class MyAbstractAspectAdvice {

    private Method method;

    private Object aspectTarget;

    public MyAbstractAspectAdvice(Method method, Object aspectTarget) {
        this.method = method;
        this.aspectTarget = aspectTarget;
    }
}
