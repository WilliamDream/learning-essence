package com.william.order.service;

import com.william.api.model.Order;
import com.william.api.service.OrderService;
import com.william.order.mapper.OrderMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderMapper orderMapper;

    /**
     * 创建订单
     */
    @Override
    public boolean createOrder(Integer userId, Integer itemId, Integer num) {
        Order order = new Order();
        order.setUserId(userId);
        order.setPrice(new BigDecimal(10));
        order.setNum(num);
        order.setTotal(new BigDecimal(num*10));
        order.setItemId(itemId);
        order.setOrderStatus(0);
        order.setPayStatus(0);
        order.setCloseTime(null);
        order.setCreateTime(new Date());
        orderMapper.insert(order);
        return true;
    }

    @Override
    public Order getOrderByOrderId(Integer orderId) {
        return null;
    }
}
