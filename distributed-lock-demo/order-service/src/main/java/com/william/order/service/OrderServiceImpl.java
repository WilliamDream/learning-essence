package com.william.order.service;

import com.william.api.model.Order;
import com.william.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;


public class OrderServiceImpl implements OrderService {

    @Autowired

    @Override
    public boolean createOrder(Order orderDto) {


        return false;
    }

    @Override
    public Order getOrderByOrderId(Integer orderId) {
        return null;
    }
}
