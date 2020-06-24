package com.william.order.service;

import com.william.api.model.Order;
import com.william.api.service.OrderService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service
public class OrderServiceImpl implements OrderService {


    /**
     * 创建订单
     */
    @Override
    public boolean createOrder(Order order) {
        //检查库存

        //生成订单

        //支付订单

        //增加积分

        return false;
    }

    @Override
    public Order getOrderByOrderId(Integer orderId) {
        return null;
    }
}
