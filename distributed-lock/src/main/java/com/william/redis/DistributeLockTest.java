package com.william.redis;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/5/20 20:36
 */
public class DistributeLockTest extends Thread{


    @Override
    public void run() {
        while (true){
            DistributeLock distributeLock = new DistributeLock();
            String lock = distributeLock.acquireLock("updateCount",2000,6000);
            if(lock != null){
                System.out.println(Thread.currentThread().getName()+"->获得锁成功。"+lock);
                try {
                    Thread.sleep(1000);
                    distributeLock.releaseLock("updateCount",lock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;//结束，跳出循环
            }

        }

    }

    public static void main(String[] args) {
        DistributeLockTest test = new DistributeLockTest();
        for (int i=0;i<10;i++){
            new Thread(test,"thread:"+i).start();
        }


    }

}
