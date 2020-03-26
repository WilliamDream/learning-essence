package com.william.rpc;


public class ClientTest
{
    public static void main( String[] args )
    {
        RpcProxyClient rpcProxyClient = new RpcProxyClient();
        IUserService userService = rpcProxyClient.clientProxy(IUserService.class,"localhost",8080);
        String res = userService.getUserByName("老王");
        System.out.println("客户端接收："+res);
    }
}
