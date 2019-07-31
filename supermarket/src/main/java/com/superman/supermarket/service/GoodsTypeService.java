package com.superman.supermarket.service;

import com.superman.supermarket.entity.GoodsType;
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
public interface GoodsTypeService extends IService<GoodsType> {

    /**
     *  根据条件查询商品类别
     * @param goodsType
     * @return
     */
    List<GoodsType> findByCondition(GoodsType goodsType);

    /**
     *  更具id修改商品类型名称
     * @param goodsType
     * @return
     */
    int modifyById(GoodsType goodsType);

    /**
     *  添加商品类型
     * @param goodsType
     * @return
     */
    int addGoodsType(GoodsType goodsType);

    /**
     *  根据id删除商品类型
     * @param ids
     * @return
     */
    int deleteByIds(int [] ids);

    /**
     *  根据类型id查询商品信息
     * @param ids
     * @return
     */
    Boolean selectGoodsByGoodsType(int [] ids);
}
