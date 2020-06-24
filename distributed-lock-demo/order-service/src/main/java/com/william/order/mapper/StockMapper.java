package com.william.order.mapper;

import com.william.api.model.Stock;

public interface StockMapper {
    int deleteByPrimaryKey(Integer itemId);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer itemId);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);
}