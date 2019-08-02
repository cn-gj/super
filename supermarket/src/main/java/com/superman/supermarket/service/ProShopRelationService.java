package com.superman.supermarket.service;

import com.superman.supermarket.entity.ProShopRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface ProShopRelationService extends IService<ProShopRelation> {

    /**
     * 往中间表添加门店和供应商信息
     * @param proShopRelation
     * @return
     */
    Integer addProShop(ProShopRelation proShopRelation);
}
