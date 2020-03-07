package com.william.minispring.webmvc.servlet;

import com.william.minispring.annotation.MyRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: williamdream
 * @Date: 2019/10/10 15:31
 * @Description:
 */
public class MyHandlerAdapter {

    public boolean supports(Object handler){
        return handler instanceof MyHandlerMapping;
    }

    public MyModelAndView handle(HttpServletRequest request, HttpServletResponse response,Object handler){
        MyHandlerMapping handlerMapping = (MyHandlerMapping)handler;
        //把方法的形参与request的参数列表的顺序进行一一对应
        Map<String,Integer> paramIndexMapping = new HashMap<String,Integer>();
        Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
        for (int i=0;i<pa.length;i++){
            for (Annotation a : pa[i]){
                if(a instanceof MyRequestParam){
                    String paramName = ((MyRequestParam) a).value();
                    if(!"".equals(paramName.trim())){
                        paramIndexMapping.put(paramName,i);
                    }
                }
            }
        }

        //获取实参，获取方法中的request和response参数
        Class<?> [] paramsTypes = handlerMapping.getMethod().getParameterTypes();
        for (int i = 0; i < paramsTypes.length ; i ++) {
            Class<?> type = paramsTypes[i];
            if(type == HttpServletRequest.class ||
                    type == HttpServletResponse.class){
                paramIndexMapping.put(type.getName(),i);
            }
        }

        //获取方法的参数列表
        Class<?> [] paramTypes = handlerMapping.getMethod().getParameterTypes();
        //实参列表
        Object [] paramValues = new Object[paramTypes.length];

        Map<String,String[]> params = request.getParameterMap();
        for (Map.Entry<String,String[]> param : params.entrySet()){
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
            if(!paramIndexMapping.containsKey(param.getKey())){continue;}
            int index = paramIndexMapping.get(param.getKey());
            paramValues[index] = caseStringValue(paramTypes[index],value);
        }

        //设置方法中的request和response对象,他们两个是特殊的类型，不是比如String、Integer等
        int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
        paramValues[reqIndex] = request;
        int respIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
        paramValues[respIndex] = response;

        Object result = null;
        try {
            result = handlerMapping.getMethod().invoke(handlerMapping.getController(), paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if(result == null||result instanceof Void)
            return null;
        boolean isModelAndView = handlerMapping.getMethod().getReturnType() == MyModelAndView.class;
        if(isModelAndView){
            return (MyModelAndView)result;
        }
        return null;
    }

    //类型转换
    private Object caseStringValue(Class<?> paramType, String value) {
        if(String.class == paramType){
            return value;
        }else if(Integer.class == paramType){
            return Integer.valueOf(value);
        }else if (Double.class == paramType){
            return Double.valueOf(value);
        }else {
            if(value != null){
                return value.toString();
            }
        }
        //如果还有double或者其他类型，继续加if
        //这时候，我们应该想到策略模式了
        //在这里暂时不实现，希望小伙伴自己来实现
        return value;
    }

}
