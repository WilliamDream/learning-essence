package com.william.rpc.transport;

import com.william.rpc.remote.RpcRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private String host;
    private int port;
    private Socket socket;

    private OutputStream outputStream;

    private InputStream inputStream;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public boolean isConnection(){
        if(socket!=null&&!socket.isClosed()&&socket.isBound()){
            return true;
        }
        try {
            socket = new Socket(host,port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Object invoker(RpcRequest request){
        if(isConnection()){
            try {
                outputStream.write(JavaSerializeUtil.serialize(request));
                byte [] res = new byte[1024];
                return JavaSerializeUtil.deserialize(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
