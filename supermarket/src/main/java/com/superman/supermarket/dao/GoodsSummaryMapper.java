package com.superman.supermarket.dao;

import com.superman.supermarket.entity.vo.GoodsSummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsSummaryMapper {
    /**
     * 统计热销商品
     * @return
     */
    List<GoodsSummary> selGoodsSellCake(@Param("limit") Integer limit);
}
