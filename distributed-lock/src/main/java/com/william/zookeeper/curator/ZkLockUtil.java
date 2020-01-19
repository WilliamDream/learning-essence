package com.william.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: chaiz
 * @Date: 2020/1/17 16:05
 * @Description:
 */
public class ZkLockUtil {
    private static final int BASE_SLEEP_TIME_MS = 5000; //定义失败重试间隔时间 单位:毫秒
    private static final int MAX_RETRIES = 3; //定义失败重试次数
    private static final int SESSION_TIME_OUT = 30000; //定义会话存活时间,根据业务灵活指定 单位:毫秒
    private static final String ZK_URI = "192.168.0.63:2181";
    private static final String NAMESPACE = "zk_distribute_lock";   //命名空间

    //分布式锁,用于挂起当前线程,等待上一把分布式锁释放
    private static CountDownLatch DISTRIBUTE_LOCK = new CountDownLatch(1);
    //分布式锁的总结点名
    private final static String ZK_LOCK_PROJECT = "zk-lock";
    //分布式锁节点名
    private final static String DISTRIBUTE_LOCK_NAME = "distribute-lock";

    /**
     * 与zk建立连接
     * @return
     */
    public static CuratorFramework build(){
        //创建比较简单,链式编程,很爽,基本上指定点参数就OK了
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME_MS,MAX_RETRIES);//重试策略
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(ZK_URI)
                .retryPolicy(retryPolicy)
                .namespace(NAMESPACE)
                .sessionTimeoutMs(SESSION_TIME_OUT)
                .build();
        return client;
    }
    /**
     * 获取分布式锁
     */
    public static void getLock() {
        CuratorFramework client = build();
        client.start(); //开启会话
        while (true) {
            try {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/" + ZK_LOCK_PROJECT + "/" + DISTRIBUTE_LOCK_NAME);
                System.out.println(Thread.currentThread().getName()+"---获取分布式锁成功...");
                return;
            } catch (Exception e) {
                try {
                    //如果没有获取到锁,需要重新设置同步资源值
                    if (DISTRIBUTE_LOCK.getCount() <= 0) {
                        DISTRIBUTE_LOCK = new CountDownLatch(1);
                    }
                    System.out.println(Thread.currentThread().getName()+"---获取分布式锁失败,等待他人释放锁中...");
                    DISTRIBUTE_LOCK.await();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }

    /**
     * 释放锁资源 1 2 3 4 5 6 7 8 9 10
     */
    public static void  release(String path) {
        CuratorFramework client = build();
        client.start();
        try {
            client.delete().forPath(path);
            System.out.println("**********************************************"+Thread.currentThread().getName()+"---锁释放成功...");
            DISTRIBUTE_LOCK.countDown();
        } catch (Exception e) {
            System.out.println("释放锁失败...");
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    /**
     * 为指定路径节点创建watch,观察锁状态
     */
    public static void addWatcher2Path(final String path) throws Exception {
        CuratorFramework client = build();
        client.start();
        final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        System.out.println("创建观察者成功...");
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                if (pathChildrenCacheEvent.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                    String nodePath = pathChildrenCacheEvent.getData().getPath();
                    System.out.println("上一会话已释放锁或会话已断开...节点路径为:" + nodePath);
                    if (nodePath.contains(DISTRIBUTE_LOCK_NAME)) {
                        DISTRIBUTE_LOCK.countDown();
                        System.out.println("释放计数器,计数器值为:"+DISTRIBUTE_LOCK.getCount()+"让当前请求来获取分布式锁...");
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        final ExecutorService threadpool = Executors.newCachedThreadPool();
        System.out.println("开始购买...");
        for (int i = 0; i <10 ; i++) {
            threadpool.execute(new Runnable() {
                public void run() {
                    System.out.println("我是线程:"+Thread.currentThread().getName()+"我开始抢购了...");
                    ZkLockUtil.getLock();
                    System.out.println(Thread.currentThread().getName()+":我正在疯狂的剁手购买中...");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+":我买完了,有请下一位...");
                    try {
                        ZkLockUtil.addWatcher2Path("/zk-lock");
                        System.out.println("释放完毕...");
                        ZkLockUtil.release("/zk-lock/distribute-lock");
                        System.out.println("添加完毕...");
                        Thread.sleep(1000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}
