package com.william.rpc.remote;


import java.io.Serializable;

public class RpcRequest implements Serializable{

    //请求类名
    private String className;
    //请求方法名
    private String methodName;
    //请求方法参数
    private Object[] parameters;
    //请求参数类型
    private Class[] types;


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Class[] getTypes() {
        return types;
    }

    public void setTypes(Class[] types) {
        this.types = types;
    }
}
