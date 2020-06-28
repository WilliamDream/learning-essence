package com.william.shopping.utils;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: WilliamDream
 * @Date: 2020/1/19 14:21
 * @Description:
 */
public class DistributedLockUtil {

    private static final Logger logger = LoggerFactory.getLogger(DistributedLockUtil.class);
    private static final int BASE_SLEEP_TIME_MS = 5000; //定义失败重试间隔时间 单位:毫秒
    private static final int MAX_RETRIES = 3; //定义失败重试次数
    private static final int SESSION_TIME_OUT = 30000;
    private static final String ZK_URI = "192.168.0.63:2181";
    private final static String ROOT_PATH_LOCK = "rootlock";
    private final static String ORDER_PATH_LOCK = "path";
    private static final String NAMESPACE = "zk_distribute_lock";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

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
    public static void acquireDistributedLock(String ORDER_PATH_LOCK) {
        String keyPath = "/" + ROOT_PATH_LOCK + "/" + ORDER_PATH_LOCK;
        CuratorFramework curatorFramework = build();
        curatorFramework.start();
        while (true) {
            try {
                curatorFramework
                        .create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                        .forPath(keyPath);
                System.out.println("success to acquire lock for path:{"+keyPath+"}");
                break;
            } catch (Exception e) {
                System.out.println("failed to acquire lock for path:{"+keyPath+"}");
                logger.info("while try again .......");
                try {
                    if (countDownLatch.getCount() <= 0) {
                        countDownLatch = new CountDownLatch(1);
                    }
                    countDownLatch.await();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 释放分布式锁
     */
    public static boolean releaseDistributedLock(String ORDER_PATH_LOCK) {
        try {
            String keyPath = "/" + ROOT_PATH_LOCK + "/" + ORDER_PATH_LOCK;
            CuratorFramework curatorFramework = build();
            curatorFramework.start();
            if (curatorFramework.checkExists().forPath(keyPath) != null) {
                curatorFramework.delete().forPath(keyPath);
            }
            System.out.println(Thread.currentThread().getName()+"线程释放锁成功！");
            countDownLatch.countDown();
        } catch (Exception e) {
            System.out.println("failed to release lock");
            return false;
        }
        return true;
    }

    /**
     * 创建 watcher 事件
     */
    public static void addWatcher() throws Exception {
        String keyPath;
        if (ORDER_PATH_LOCK.equals(ROOT_PATH_LOCK)) {
            keyPath = "/" + ORDER_PATH_LOCK;
        } else {
            keyPath = "/" + ROOT_PATH_LOCK + "/" + ORDER_PATH_LOCK;
        }
        CuratorFramework curatorFramework = build();
        curatorFramework.start();
        final PathChildrenCache cache = new PathChildrenCache(curatorFramework, keyPath, false);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                if (pathChildrenCacheEvent.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                    String nodePath = pathChildrenCacheEvent.getData().getPath();
                    System.out.println("上一会话已释放锁或会话已断开...节点路径为:" + nodePath);
                    if (nodePath.contains(ORDER_PATH_LOCK)) {
                        countDownLatch.countDown();
                        System.out.println("释放计数器,计数器值为:"+countDownLatch.getCount()+"让当前请求来获取分布式锁...");
                    }
                }
            }
        });
        /*cache.getListenable().addListener((client, event) -> {
            if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                String oldPath = event.getData().getPath();
                System.out.println("success to release lock for path:{"+oldPath+"}");
                if (oldPath.contains(path)) {
                    //释放计数器，让当前的请求获取锁
                    countDownLatch.countDown();
                }
            }
        });*/
    }

    /*public static void main(String[] args) {
        final ExecutorService threadpool = Executors.newCachedThreadPool();
        System.out.println("开始购买...");
        for (int i = 0; i <10 ; i++) {
            threadpool.execute(new Runnable() {
                public void run() {
                    System.out.println("我是线程:"+Thread.currentThread().getName()+"我开始抢购了...");
                    acquireDistributedLock("orderlock");
                    System.out.println(Thread.currentThread().getName()+":我正在疯狂的剁手购买中...");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+":我买完了,有请下一位...");
                    try {
                        addWatcher("orderlock");
                        releaseDistributedLock("orderlock");
                        System.out.println("释放完毕...");
                        Thread.sleep(1000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }*/

}
