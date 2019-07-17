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
   public String findShopTypeList();


    /**
     * 添加门店类别信息
     * @param shopType
     * @return
     */
    Integer addShopType(ShopType shopType);


    /**
     * 修改门店类别信息
     * @param shopType
     * @return
     */
    Integer upShopType(ShopType shopType);

    /**
     * 删除门店类别信息
     * @param id
     * @return
     */
    Integer delShopType(Integer id);
}
