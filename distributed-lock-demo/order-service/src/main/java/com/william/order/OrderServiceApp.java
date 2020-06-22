package com.william.order;

import com.william.order.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *
 */
@SpringBootApplication
public class OrderServiceApp
{

    public static void main( String[] args )
    {
        SpringApplication.run(OrderServiceApp.class,args);
    }
}
