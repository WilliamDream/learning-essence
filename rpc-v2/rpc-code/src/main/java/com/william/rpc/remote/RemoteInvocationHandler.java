package com.william.rpc.remote;


import com.william.rpc.transport.Client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler{

    private String host;
    private int port;


    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        System.out.println("请求类"+method.getDeclaringClass());
        System.out.println("请求类名"+method.getDeclaringClass().getName());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setTypes(method.getParameterTypes());
        Client rpcNetTransport = new Client(host,port);
        return rpcNetTransport.invoker(request);
    }
}
