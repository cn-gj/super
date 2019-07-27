package com.superman.supermarket.dao;

import com.superman.supermarket.entity.ProShopRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface ProShopRelationMapper extends BaseMapper<ProShopRelation> {

    /**
     * 往中间表添加门店和供应商信息
     * @param proShopRelation
     * @return
     */
    Integer addProShop(ProShopRelation proShopRelation);
}
