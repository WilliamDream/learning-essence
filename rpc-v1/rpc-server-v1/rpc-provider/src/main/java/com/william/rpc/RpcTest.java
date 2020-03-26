package com.william.rpc;


public class RpcTest {

    public static void main(String[] args) {
        IUserService userService = new UserService();
        RpcProxyServer proxyServer = new RpcProxyServer();
        proxyServer.publisher(userService,8080);
    }

}
