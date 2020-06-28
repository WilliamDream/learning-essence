package com.william.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @Auther: WilliamDream
 * @Date: 2020/1/13 17:28
 * @Description:
 */
public class CuratorDemo {

    private static String CONNECTION_STR = "192.168.0.63:2181";

    public static void main(String[] args) {
        try {
            CuratorFramework client = CuratorFrameworkFactory.builder().
                    connectString(CONNECTION_STR).sessionTimeoutMs(50000000).
                    //重试策略，表示客户端的超时重连策略
                    retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
            client.start();
            //创建 /william 的根节点，初始内容为空。
//            client.create().forPath("/william");
            //创建节点带有初始内容
//            client.create().forPath("/cars","benz".getBytes());
            //创建临时节点
//            client.create().withMode(CreateMode.EPHEMERAL).forPath("/how","good".getBytes());
            //递归创建多级节点
//            client.create().creatingParentsIfNeeded().forPath("/city/area");
            //创建临时有序节点
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/demp/hr");
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/demp/dev");
            //判断节点是否存在
            client.checkExists().forPath("/demp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
