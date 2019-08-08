package com.superman.supermarket.service.impl;

import com.superman.supermarket.dao.GoodsSummaryMapper;
import com.superman.supermarket.dao.ShopSummaryMapper;
import com.superman.supermarket.entity.vo.GoodsSummary;
import com.superman.supermarket.entity.vo.ShopSummary;
import com.superman.supermarket.service.ShopSummaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShopSummaryServiceImpl implements ShopSummaryService {
    @Resource
    private ShopSummaryMapper shopSummaryMapper;

    @Resource
    private GoodsSummaryMapper goodsSummaryMapper;

    @Override
    public List<ShopSummary> shopSummary() {
        return shopSummaryMapper.shopSummary();
    }

    /**
     * 统计热销商品
     * @param limit
     * @return
     */
    @Override
    public List<GoodsSummary> selGoodsSellCake(Integer limit) {
        return goodsSummaryMapper.selGoodsSellCake(limit);
    }
}
