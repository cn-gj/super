package com.superman.supermarket.dao;

import com.superman.supermarket.entity.Goods;
import com.superman.supermarket.entity.GoodsType;
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
public interface GoodsTypeMapper extends BaseMapper<GoodsType> {

    /**
     *  根据类型id查询商品信息
     * @param ids
     * @return
     */
    Long selectGoodsByGoodsType(@Param("ids")int [] ids);

    /**
     *  根据id删除商品类型
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") int [] ids);
}
