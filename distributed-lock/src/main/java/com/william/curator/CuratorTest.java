package com.william.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/5/20 22:43
 */
public class CuratorTest {

    public static void main(String[] args) {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString();
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework,"/userservice");


        try {
            interProcessMutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
