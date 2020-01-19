package com.william.api.service;

import com.william.api.pojo.dto.OrderDto;
import com.william.api.service.hystrix.OrderServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: chaiz
 * @Date: 2020/1/19 17:29
 * @Description:
 */
@FeignClient(serviceId = "order-service",fallback = OrderServiceHystrix.class)
public interface OrderService {


    boolean createOrder(OrderDto orderDto);
}
