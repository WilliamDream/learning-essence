package com.william.minispring.framework.aop.support;

import com.sun.istack.internal.Nullable;
import com.william.minispring.framework.aop.adapter.MyAfterReturningAdviceInterceptor;
import com.william.minispring.framework.aop.adapter.MyMethodBeforeAdviceInterceptor;
import com.william.minispring.framework.aop.adapter.MyThrowsAdviceInterceptor;
import com.william.minispring.framework.aop.config.MyAopConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: williamdream
 * @Date: 2010/10/11 15:13
 * @Description:
 */
public class MyAdvisedSupport {

    private Class<?> targetClass;

    private Object target;

    private MyAopConfig config;

    //切点正则
    private Pattern pointCutClassPattern;

    private transient Map<Method,List<Object>> methodCache;

    public MyAdvisedSupport(MyAopConfig config) {
        this.config = config;
    }


    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, @Nullable Class<?> targetClass) throws Exception{
        List<Object> cached = methodCache.get(method);
        if(cached!=null){
            Method m = targetClass.getMethod(targetClass.getName(),method.getParameterTypes());
            this.methodCache.put(m,cached);
        }
        return cached;
    }

    //把符合切面表达式的类扫描出来
    public Class<?> getTargetClass(){
        return null;
    }

    public Object getTarget(){
        return null;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    private void parse() {
        String pointCut = config.getPointCut().replaceAll("\\.","\\\\.")
                .replaceAll("\\\\.\\*",".*")
                .replaceAll("\\(","\\\\(")
                .replaceAll("\\)","\\\\)");
        //正则 class com.william.xx.xx.*Service
        String pointCutForClassRegex = pointCut.substring(0,pointCut.lastIndexOf("\\(")-4);
        pointCutClassPattern = Pattern.compile("class"+pointCutForClassRegex.substring(
                pointCutForClassRegex.lastIndexOf(" ")+1
        ));

        try {
            methodCache = new HashMap<>();
            Class aspectClass = Class.forName(this.config.getAspectClass());
            Map<String, Method> aspectMethods = new HashMap<String,Method>();
            for (Method method : aspectClass.getMethods()){
                aspectMethods.put(method.getName(),method);
            }


            Pattern pattern = Pattern.compile(pointCut);
            for(Method m : this.targetClass.getMethods()){
                String methodString = m.toString();
                if(methodString.contains("throws")){
                    methodString = methodString.substring(0,methodString.lastIndexOf("throws")).trim();
                }
                Matcher matcher = pattern.matcher(methodString);

                if(matcher.matches()){
                    //如果可以匹配到，则把方法包装为MethodInterceptor
                    List<Object> advices = new LinkedList<>();
                    if(!(config.getAspectBefore()==null||"".equals(config.getAspectBefore()))){
                        advices.add(new MyMethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()),aspectClass));
                    }
                    if(!(config.getAspectAfter()==null||"".equals(config.getAspectAfter()))){
                        advices.add(new MyAfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectAfter()),aspectClass));
                    }
                    if(!(config.getAspectAfterThrow()==null||"".equals(config.getAspectAfterThrow()))){
                        MyThrowsAdviceInterceptor interceptor = new MyThrowsAdviceInterceptor(aspectMethods.get(config.getAspectAfterThrow()),aspectClass);
                        interceptor.setThrowName(config.getAspectAfterThrowingName());
                        advices.add(interceptor);
                    }
                    methodCache.put(m,advices);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }
}
