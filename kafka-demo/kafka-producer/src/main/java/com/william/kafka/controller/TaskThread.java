package com.william.kafka.controller;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2020/4/26 7:44
 */
public class TaskThread implements Runnable{

    @Override
    public void run() {
            System.out.println("我在执行一项耗时任务");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成");

    }
}
