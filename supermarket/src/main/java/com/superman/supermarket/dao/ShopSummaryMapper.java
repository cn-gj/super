package com.superman.supermarket.dao;

import com.superman.supermarket.entity.vo.ShopSummary;

import java.util.List;

/**
 * 报表
 */
public interface ShopSummaryMapper {

    /**
     * 店铺汇总
     * @return
     */
    List<ShopSummary> shopSummary();
}
