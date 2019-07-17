package com.superman.supermarket.service;

import com.superman.supermarket.entity.ShopType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface ShopTypeService extends IService<ShopType> {
    /**
     * 查询所有门店类别
     * @return
     */
   public List<ShopType> findShopTypeList();


}
