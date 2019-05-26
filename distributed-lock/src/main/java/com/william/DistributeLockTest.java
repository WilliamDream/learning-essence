package com.william;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/5/20 22:08
 */
public class DistributeLockTest {


    public static void main( String[] args ) throws IOException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i=0;i<10;i++){
            new Thread(()->{
                try {
                    countDownLatch.await();
                    DistributedLock distributedLock=new DistributedLock();
                    distributedLock.lock(); //获得锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"Thread-"+i).start();
            countDownLatch.countDown();
        }
        System.in.read();
    }

}
