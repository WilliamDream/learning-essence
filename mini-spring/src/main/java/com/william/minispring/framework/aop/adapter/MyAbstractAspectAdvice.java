package com.william.minispring.framework.aop.adapter;

import com.william.minispring.framework.aop.intercept.MyJoinPoint;

import java.lang.reflect.Method;

/**
 * @Auther: williamdream
 * @Date: 2019/10/12 22:32
 * @Description:
 */
public abstract class MyAbstractAspectAdvice {

    private Method aspectmethod;

    private Object aspectTarget;

    public MyAbstractAspectAdvice(Method aspectmethod, Object aspectTarget) {
        this.aspectmethod = aspectmethod;
        this.aspectTarget = aspectTarget;
    }

    public Object invokeAdviceMethod(MyJoinPoint joinPoint,Object returnValue,Throwable tx) throws Exception{
        Class<?> [] paramTypes = this.aspectmethod.getParameterTypes();
        if(paramTypes == null||paramTypes.length == 0){
            return this.aspectmethod.invoke(aspectTarget);
        }else {
            Object [] args = new Object[paramTypes.length];
            for (int i=0;i<args.length;i++){
                if(paramTypes[i] == MyJoinPoint.class){
                    args[i] = joinPoint;
                }else if(paramTypes[i] == Throwable.class){
                    args[i] = tx;
                }else if(paramTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }
            return this.aspectmethod.invoke(aspectTarget,args);
        }
    }


}
