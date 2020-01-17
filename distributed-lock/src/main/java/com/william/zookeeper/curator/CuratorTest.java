package com.william.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/5/20 22:43
 */
public class CuratorTest {

    public static void main(String[] args) {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.0.63:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .build();
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework,"/userservice");


        try {
            interProcessMutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
