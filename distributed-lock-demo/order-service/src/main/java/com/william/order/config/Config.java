package com.william.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @Value("guojin.app")
    private String guojinapp;





}
