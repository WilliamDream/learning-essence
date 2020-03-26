package com.william.rmi.register;


import org.apache.curator.framework.CuratorFramework;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/5/26 11:41
 */
public class RpcRegisterCenterImpl implements IRpcRegisterCenter {

    private CuratorFramework curatorFramework;

    @Override
    public void register(String serviceName, String serviceAddr) {

    }
}
