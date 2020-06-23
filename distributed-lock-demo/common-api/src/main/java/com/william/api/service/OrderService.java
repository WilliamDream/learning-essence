package com.william.api.service;

import com.william.api.model.Order;
import com.william.api.service.hystrix.OrderServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: chaiz
 * @Date: 2020/1/19 17:29
 * @Description:
 */
@FeignClient(serviceId = "order-service",fallback = OrderServiceHystrix.class)
public interface OrderService {

    @PostMapping("/order/createOrder")
    boolean createOrder(@RequestBody Order orderDto);


    @GetMapping("/order/getOrderByOrderId/{orderId}")
    Order getOrderByOrderId(@PathVariable("orderId") Integer orderId);
}
