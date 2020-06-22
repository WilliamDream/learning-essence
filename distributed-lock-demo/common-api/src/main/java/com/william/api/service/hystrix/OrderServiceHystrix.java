package com.william.api.service.hystrix;

import com.william.api.pojo.dto.OrderDto;
import com.william.api.service.OrderService;
import org.springframework.stereotype.Component;

/**
 * @Auther: chaiz
 * @Date: 2020/1/19 17:30
 * @Description:
 */

@Component
public class OrderServiceHystrix implements OrderService {

    @Override
    public boolean createOrder(OrderDto orderDto) {
        return false;
    }

    @Override
    public OrderDto getOrderByOrderId(Integer orderId) {
        return null;
    }
}
