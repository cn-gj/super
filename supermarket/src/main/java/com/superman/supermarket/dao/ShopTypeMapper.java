package com.superman.supermarket.dao;

import com.superman.supermarket.entity.ShopType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface ShopTypeMapper extends BaseMapper<ShopType> {

    /**
     * 查询所有门店分类
     * @return
     */
    List<ShopType> findAllShopType();

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
    Integer delShopType(@Param("id") Integer id);
}
