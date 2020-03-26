package com.william.rpc;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessHandler implements Runnable{

    private Socket socket;

    private Object service;

    public ProcessHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;

        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest) inputStream.readObject();
            Object result = invoke(rpcRequest);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    private Object invoke(RpcRequest request)throws ClassNotFoundException,NoSuchMethodException,InvocationTargetException,IllegalAccessException {
        //获得客户端参数
        Object [] args = request.getParameters();
        //获得每个参数类型
        Class<?>[] types = new Class[args.length];
        for (int i=0;i<args.length;i++){
            types[i] = args[i].getClass();
        }

        Class clazz = Class.forName(request.getClassName());
        Method method = clazz.getMethod(request.getMethodName(),types);
        Object object = method.invoke(service,args);
        return object;

    }

}
