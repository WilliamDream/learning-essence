package com.william.kafka.controller;


import com.william.kafka.TaskConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/test")
public class NewsController {

//    @Autowired
//    private TaskConfig taskExecutor;

    ExecutorService executor = Executors.newCachedThreadPool();

    @GetMapping("/th")
    public String Test(HttpServletRequest request){
        System.out.println("开始");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TaskThread t = new TaskThread();
        executor.execute(t);
        System.out.println("结束");
        return "success";
    }


    @Async
    public void longtime() {
        System.out.println("我在执行一项耗时任务");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("完成");
        executor.shutdown();
    }
}
