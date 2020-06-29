package com.william.shopping.service;

import com.william.api.service.OrderService;
import com.william.api.service.StockService;
import com.william.shopping.utils.DistributedLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingService {

    @Autowired
    private StockService stockService;

    @Autowired
    private OrderService orderService;


    public String shopping(Integer userId, Integer itemId,Integer num){
        //获取分布式锁
        DistributedLockUtil.acquireDistributedLock();
        try {
            //获取商品库存
            Integer stockNum = stockService.getStockByProductId(itemId);
            if(stockNum<num){
                return "库存不足";
            }
            orderService.createOrder(userId,itemId,num);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放分布式锁
            DistributedLockUtil.releaseDistributedLock();
        }
        return "下单成功";
    }

}
