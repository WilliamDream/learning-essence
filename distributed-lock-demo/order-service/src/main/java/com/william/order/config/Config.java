package com.william.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @Value("${guojinziguan.client_id}")
    private String appKey;

    public void getConfig(){
        System.out.println(appKey);
    }

}
