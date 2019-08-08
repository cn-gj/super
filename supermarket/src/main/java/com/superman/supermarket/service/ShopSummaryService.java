package com.superman.supermarket.service;

import com.superman.supermarket.entity.vo.GoodsSummary;
import com.superman.supermarket.entity.vo.ShopSummary;

import java.util.List;

public interface ShopSummaryService {
    /**
     * 店铺汇总
     * @return
     */
    List<ShopSummary> shopSummary();

    /**
     * 统计热销商品
     * @return
     */
    List<GoodsSummary> selGoodsSellCake(Integer limit);
}
