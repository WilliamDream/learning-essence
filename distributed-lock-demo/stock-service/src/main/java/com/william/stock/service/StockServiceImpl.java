package com.william.stock.service;

import com.william.api.model.Stock;
import com.william.api.service.StockService;
import com.william.stock.mapper.StockMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;


    @Override
    public Integer getStockByProductId(Integer itemId) {
        Stock stock = stockMapper.selectByPrimaryKey(itemId);
        return stock!=null?stock.getStockCount():0;
    }

    @Override
    public boolean updateStockByProductId(Integer productId, Integer subCount) {

        return false;
    }
}
