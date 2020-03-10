package com.william.minispring.framework.aop.intercept;

import java.lang.reflect.Method;

/**
 * @Auther: williamdream
 * @Date: 2019/10/12 22:10
 * @Description:
 */
public interface MyJoinPoint {

    Object getThis();

    Object[] getArguments();

    Method getMethod();


}
