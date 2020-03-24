package com.william.rmi.register;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/5/26 11:38
 */
public interface IRpcRegisterCenter {

    /**
       * @Description 注册服务
       * @param serviceName
     * @param serviceAddr
       * @return void
       * @Date 2019/5/26 11:40
       */
    void register(String serviceName, String serviceAddr);

}
