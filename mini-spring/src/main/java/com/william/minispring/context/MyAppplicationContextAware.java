package com.william.minispring.context;

/**
 * @Author: WilliamDream
 * @Description: 通过解耦的方式获取IOC容器的顶层设计
 * 后面将通过一个监听器去扫描所有的类，只要实现此接口，将自动
 * 调用setApplicationContext()方法，从而将IOC容器注入到目标类中。
 * @Date: 2019/9/22 16:42
 */
public interface MyAppplicationContextAware {

    void setApplicationContext(MyApplicationContext applicationContext);

}
