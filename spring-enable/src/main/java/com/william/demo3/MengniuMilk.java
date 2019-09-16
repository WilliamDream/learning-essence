package com.william.demo3;

import org.springframework.stereotype.Component;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/15 21:45
 */

@Component
public class MengniuMilk implements Milk {

    @Override
    public void processMilk() {
        System.out.println("加工蒙牛牛奶");
    }

    @Override
    public void getMilk() {
        System.out.println("获取蒙牛牛奶");
    }
}
