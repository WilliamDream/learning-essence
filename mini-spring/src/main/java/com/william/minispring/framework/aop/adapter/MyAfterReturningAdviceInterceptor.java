package com.william.minispring.framework.aop.adapter;

import com.william.minispring.framework.aop.intercept.MyJoinPoint;
import com.william.minispring.framework.aop.intercept.MyMethodInterceptor;
import com.william.minispring.framework.aop.intercept.MyMethodInvocation;

import java.lang.reflect.Method;

/**
 * @Auther: williamdream
 * @Date: 2019/10/12 22:24
 * @Description:
 */
public class MyAfterReturningAdviceInterceptor extends MyAbstractAspectAdvice implements MyMethodInterceptor {

    private MyJoinPoint joinPoint;

    public MyAfterReturningAdviceInterceptor(Method method, Object aspectTarget) {
        super(method, aspectTarget);
    }

    @Override
    public Object invoke(MyMethodInvocation mi) throws Throwable {
        Object reVal = mi.proceed();
        this.joinPoint = mi;
        this.afterReturning(reVal,mi.getMethod(),mi.getArguments(),mi.getThis());
        return reVal;
    }

    public void afterReturning(Object reVal, Method method, Object[] arguments, Object aThis) throws Exception {
        super.invokeAdviceMethod(this.joinPoint,reVal,null);
    }
}
