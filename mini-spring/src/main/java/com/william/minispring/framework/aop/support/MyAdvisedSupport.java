package com.william.minispring.framework.aop.support;

import com.sun.istack.internal.Nullable;
import com.william.minispring.framework.aop.config.MyAopConfig;
import com.william.minispring.framework.aop.intercept.MyMethodInvocation;

import javax.print.DocFlavor;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
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

    public MyAdvisedSupport(MyAopConfig config) {
        pointCutClassPattern.matcher(this.targetClass.get)
    }

    public boolean pointCutMatch(){
        return true;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, @Nullable Class<?> targetClass) {

        return null;
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
                    if(config.getAspectBefore()==null||"".equals(config.getAspectBefore())){
                        
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
