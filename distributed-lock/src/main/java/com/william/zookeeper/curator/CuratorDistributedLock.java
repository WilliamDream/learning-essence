package com.william.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: chaiz
 * @Date: 2020/1/17 14:13
 * @Description: https://www.jianshu.com/p/df99f8a371ae
 */
public class CuratorDistributedLock {

//    InterProcessMutex：分布式可重入排它锁
//    InterProcessSemaphoreMutex：分布式排它锁
//    InterProcessReadWriteLock：分布式读写锁
//    InterProcessMultiLock：将多个锁作为单个实体管理的容器
    private static String CONNECTION_STR = "192.168.0.63:2181";

    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.builder().
                connectString(CONNECTION_STR).sessionTimeoutMs(50000000).
                //重试策略，表示客户端的超时重连策略
                        retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        client.start();
        InterProcessMutex lock = new InterProcessMutex(client,"/lock_path");
        CountDownLatch down = new CountDownLatch(1);
        for (int i=0;i<10;i++){
            new Thread(()->{
                try {
                    down.await();
                    lock.acquire();
                    Thread thread = Thread.currentThread();
                    System.out.println("线程--"+thread.getName()+"获取到了锁");
                    thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Thread thread = Thread.currentThread();
                    lock.release();
                    System.out.println("线程--"+thread.getName()+"释放了锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        down.countDown();

    }







}
