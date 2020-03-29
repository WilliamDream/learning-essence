package com.william.rpc.transport;

import com.william.rpc.container.IocContainer;
import com.william.rpc.remote.RpcRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {



    public void bind(int port){
        ServerSocket serverSocket = null;

        Socket socket = null;

        OutputStream outputStream = null;

        InputStream inputStream = null;

        try {
            serverSocket = new ServerSocket(port);
            while (true){
                socket = serverSocket.accept(); //建立连接
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                byte [] request = new byte[1024];
                while (true){
                    RpcRequest rpcRequest = JavaSerializeUtil.deserialize(request);
                    Class clazz = null;
                    if(IocContainer.ioc.contains(rpcRequest.getClassName())){
                        clazz = IocContainer.ioc.get(rpcRequest.getClassName());
                        try {
                            Method method = clazz.getMethod(rpcRequest.getMethodName(),rpcRequest.getTypes());
                            Object result = method.invoke(clazz.newInstance(),rpcRequest.getParameters());
                            outputStream.write(JavaSerializeUtil.serialize(request));
                            outputStream.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
