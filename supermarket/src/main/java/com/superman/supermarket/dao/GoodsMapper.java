package com.superman.supermarket.dao;

import com.superman.supermarket.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.INTERNAL;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     *  根据条件查询商品信息
     * @param goodsVo
     * @return
     */
    List<GoodsVo> findByCondition(GoodsVo goodsVo);

    /**
     *  根据商品名称查询商品
     * @param goodsName
     * @return
     */
    Goods findByGoodsName(String goodsName);

    /**
     *  根据商品条码查询商品
     * @param goodsCode
     * @return
     */
    Goods findByGoodsCode(String goodsCode);

    /**
     *  批量删除商品
     * @param ids
     * @return
     */
    int batchDelete(int[] ids);

    /**
     * 批量修改商品类型
     * @param ids
     * @param goodsTypeId
     * @return
     */
    int batchModify(@Param("ids") int[] ids,@Param("goodsTypeId") int goodsTypeId);

    /**
     *  根据id查询商品信息
     * @param id
     * @return
     */
    List<GoodsVo> findById(@Param("id") Integer id, @Param("providerId") Integer providerId);


    /**
     *  根据商品id数组查询商品名称
     * @param noIds
     * @return
     */
    List<String> findGoodsNameByIds(@Param("noIds") List<Integer> noIds);

    /**
     * 根据商品条码查询库存中的商品
     * @param goodsCode
     * @return
     */
    GoodsVo selGoodsAndInventoryDetailByGoodsCode(String goodsCode);
}
