package com.william.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

public class CuratorDemo {

    private static String CONNECTION_STR = "192.168.0.63:2181";

    public static void main(String[] args) {
        CuratorFramework curator = CuratorFrameworkFactory.builder().
                connectString(CONNECTION_STR).
                sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).
                build();
        curator.start();


        try {
//            setData(curator);
//            createData(curator);
            createDataAcl(curator);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建路径
     * @param curatorFramework
     * @throws Exception
     */
    private static void createPath(CuratorFramework curatorFramework)throws Exception{
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .forPath("/data/images").getBytes();
    }

    /**
     * 给路径赋值
     * @param curatorFramework
     * @throws Exception
     */
    private static void setData(CuratorFramework curatorFramework)throws Exception{
        curatorFramework.setData().forPath("/data/images","pic.jpeg".getBytes());
    }

    /**
     * 创建路径同时赋值
     * @param curatorFramework
     * @throws Exception
     */
    private static void createData(CuratorFramework curatorFramework)throws Exception{
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .forPath("/data/test","v1".getBytes());
    }

    private static void createDataAcl(CuratorFramework curatorFramework)throws Exception{
        List<ACL> aclList = new ArrayList<>();
        aclList.add(new ACL(ZooDefs.Perms.READ|ZooDefs.Perms.WRITE,
                new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin"))));

        curatorFramework.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(aclList)
                .forPath("/data/auth1","v1".getBytes());
    }


    private static void deleteData(CuratorFramework curatorFramework)throws Exception{
        curatorFramework.delete().forPath("/data/test");
    }


}
