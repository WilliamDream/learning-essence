package com.william.api.service;

import com.william.api.model.Order;

/**
 * @Auther: chaiz
 * @Date: 2020/1/19 17:29
 * @Description:
 */
public interface OrderService {

    boolean createOrder(Order order);


    Order getOrderByOrderId(Integer orderId);
}
