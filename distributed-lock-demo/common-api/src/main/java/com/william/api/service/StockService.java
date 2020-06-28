package com.william.api.service;


/**
 * @Auther: williamdream
 * @Date: 2020/1/19 17:49
 * @Description:
 */
public interface StockService {

    Integer getStockByProductId(Integer itemId);

    /**
     * 更改库存
     * @param subCount
     * @param subCount 库存减少数
     * @return
     */
    boolean updateStockByProductId(Integer itemId,Integer subCount);

}
