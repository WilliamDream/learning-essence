package com.william.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: williamdream
 * @Date: 2020/1/19 17:49
 * @Description:
 */
@FeignClient(serviceId = "stock-service")
public interface StockService {

    @GetMapping("/stock/getStockByProductId/{productId}")
    Integer getStockByProductId(@PathVariable("productId") Integer productId);

    /**
     * 更改库存
     * @param productId
     * @param subCount 库存减少数
     * @return
     */
    @PostMapping("/stock/updateStockByProductId")
    boolean updateStockByProductId(@PathVariable("productId") Integer productId,@PathVariable("productId")Integer subCount);

}
