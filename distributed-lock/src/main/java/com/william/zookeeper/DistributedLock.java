package com.william.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;



/**
 * @Author: WilliamDream
 * @Description: 分布式锁之zookeeper实现
 * @Date: 2019/5/20 20:12
 */
public class DistributedLock implements Lock,Watcher{

    private String connectString = "192.168.0.63:2181";
    //根节点
    private String ROOT_NODE = "/root";
    //当前获得锁节点
    private String CURRENT_LOCK;
    //等待上一个节点
    private String WAIT_NODE;

    private ZooKeeper zookeeper = null;

    private CountDownLatch countDownLatch;


    public DistributedLock(){
        try {
            zookeeper = new ZooKeeper(connectString,4000,this);
            Stat stat = zookeeper.exists(ROOT_NODE,false);
            if (stat == null){
                zookeeper.create(ROOT_NODE,"0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean tryLock() {

        try {
            //创建临时有序节点
            CURRENT_LOCK = zookeeper.create(ROOT_NODE + "/","0".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> childrenNodes =  zookeeper.getChildren(ROOT_NODE,false);
            SortedSet<String> sortedSet = new TreeSet<>();
            for (String child : childrenNodes){
                sortedSet.add(ROOT_NODE + "/" + child);
            }
            //获取第一个节点
            String firstNode = sortedSet.first();
            SortedSet<String> node = sortedSet.headSet(CURRENT_LOCK);
            //当前节点与第一个节点进行笔记，相同则成功获取到锁
            if(CURRENT_LOCK.equals(node)){
                System.out.println(Thread.currentThread().getName()+"获得锁成功");
                return true;    //获取锁成功

            }
            if(!node.isEmpty()){
                WAIT_NODE = node.last();    //把该节点赋给下一个等待节点
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return false;
    }




    @Override
    public void lock() {
        //尝试获取锁
        if(this.tryLock()){
            //获取锁成功，则可以继续进行操作......
            System.out.println(Thread.currentThread().getName() + "====" + CURRENT_LOCK + "====" + "获取锁成功!");
            return;
        }
        //没有获取到锁，等待获取
        try {
            waitAcqureLock();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //等待获取锁
    private boolean waitAcqureLock() throws KeeperException, InterruptedException {
        Stat stat = zookeeper.exists(WAIT_NODE,true);   //监听WAIT_NODE上一个节点
        if(stat!=null){
            System.out.println(Thread.currentThread().getName() + "====" + ROOT_NODE + "/" + WAIT_NODE + "====" + "等待释放锁。");
            countDownLatch = new CountDownLatch(1);
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName() + "====" + CURRENT_LOCK + "====" + "获取锁成功!");

        }
        return true;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {



    }



    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
            System.out.println(Thread.currentThread().getName() + "====" + CURRENT_LOCK + "====" + "释放锁!");
            try {
                zookeeper.delete(CURRENT_LOCK,-1);
                CURRENT_LOCK = null;
                zookeeper.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }

        }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
            if(this.countDownLatch!=null){
                this.countDownLatch.countDown();
            }
    }
}
