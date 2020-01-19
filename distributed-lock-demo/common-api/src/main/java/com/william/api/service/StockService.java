package com.william.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther: chaiz
 * @Date: 2020/1/19 17:49
 * @Description:
 */
@FeignClient(serviceId = "stock-service")
public interface StockService {

    @GetMapping("/stock/getStockByProductId/{productId}")
    Integer getStockByProductId(@PathVariable("productId") Integer productId);

    boolean updateStockByProductId(@PathVariable("productId") Integer productId);

}
