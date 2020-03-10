package com.william.minispring.framework.aop.adapter;

import com.william.minispring.framework.aop.intercept.MyJoinPoint;
import com.william.minispring.framework.aop.intercept.MyMethodInterceptor;
import com.william.minispring.framework.aop.intercept.MyMethodInvocation;

import java.lang.reflect.Method;

/**
 * @Auther: williamdream
 * @Date: 2019/10/12 22:22
 * @Description:
 */
public class MyMethodBeforeAdviceInterceptor extends MyAbstractAspectAdvice implements MyMethodInterceptor{

    private MyJoinPoint joinPoint;

    public MyMethodBeforeAdviceInterceptor(Method method, Object aspectTarget) {
        super(method, aspectTarget);
    }

    private void before(Method method,Object[] args, Object target) throws Throwable{
        super.invokeAdviceMethod(this.joinPoint,null,null);
    }

    @Override
    public Object invoke(MyMethodInvocation mi) throws Throwable {
        //被织入的代码中才能拿到JoinPoint
        this.joinPoint = mi;
        before(mi.getMethod(),mi.getArguments(),mi.getThis());
        return mi.proceed();
    }
}
