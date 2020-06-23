package com.william.api.service.hystrix;

import com.william.api.service.StockService;
import org.springframework.stereotype.Component;

/**
 * @Auther: chaiz
 * @Date: 2020/1/19 17:50
 * @Description:
 */
@Component
public class StockServiceHystrix implements StockService {

    @Override
    public Integer getStockByProductId(Integer productId) {
        return null;
    }

    @Override
    public boolean updateStockByProductId(Integer productId, Integer subCount) {
        return false;
    }
}
