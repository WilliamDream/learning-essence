package com.william.zookeeper;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class WatcherDemo {

    private static String CONNECTION_STR = "192.168.0.63:2181";

    public static void main(String[] args) {
        CuratorFramework curator = CuratorFrameworkFactory.builder().
                connectString(CONNECTION_STR).
                sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).
                build();
        curator.start();


        try {
            addListenerWithNode(curator);
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对应节点进行监听
     * @param curatorFramework
     * @throws Exception
     */
    private static void addListenerWithNode(CuratorFramework curatorFramework)throws Exception{
        String watchPath = "/watch";
        final NodeCache nodeCache = new NodeCache(curatorFramework,watchPath,false);
        NodeCacheListener nodeCacheListener = () -> {
            System.out.print("receive Node Changed");
            System.out.println(nodeCache.getCurrentData().getPath()+"-----"+new String(nodeCache.getCurrentData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    /**
     * 对于子节点进行监听
     * @param curatorFramework
     * @throws Exception
     */
    private static void addListenerWithChildNode(CuratorFramework curatorFramework)throws Exception{
        String watchPath = "/watch";
        PathChildrenCache nodeCache = new PathChildrenCache(curatorFramework,watchPath,true);
        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework1,pathChildrenCacheEvent) -> {
            System.out.println(curatorFramework1.getData());
            System.out.print(pathChildrenCacheEvent.getType()+"- >" +new String(pathChildrenCacheEvent.getData().getData()));
        };
        nodeCache.getListenable().addListener(pathChildrenCacheListener);
        nodeCache.start(PathChildrenCache.StartMode.NORMAL);
    }



}
