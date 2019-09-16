package com.william.demo3;

import org.springframework.stereotype.Component;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/15 21:44
 */

@Component
public class YiliMilk implements Milk {

    @Override
    public void processMilk() {
        System.out.println("加工伊利牛奶");
    }

    @Override
    public void getMilk() {
        System.out.println("获取伊利牛奶");
    }
}
