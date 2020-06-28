package com.william.shopping.service;

import com.william.api.service.OrderService;
import com.william.api.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingService {

    @Autowired
    private StockService stockService;

    @Autowired
    private OrderService orderService;


    public String shopping(Integer itemId,Integer num){
        //1.获取分布式锁

        Integer stockNum = stockService.getStockByProductId(itemId);
        if(stockNum<num){

        }
        //释放分布式锁

        return "";
    }

}
