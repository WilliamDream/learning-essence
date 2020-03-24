package com.william.rpc;


public class App 
{
    public static void main( String[] args )
    {
        RpcProxyClient rpcProxyClient = new RpcProxyClient();
        IUserService userService = rpcProxyClient.clientProxy(IUserService.class,"localhost",8080);
        userService.getUserByName("老王");
    }
}
