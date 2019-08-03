package com.superman.supermarket.service;

import com.superman.supermarket.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.GoodsType;
import com.superman.supermarket.entity.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.io.OutputStream;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface GoodsService extends IService<Goods> {
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
     *  修改商品
     * @param goods
     * @return
     */
    int updateGoods(Goods goods);

    /**
     *  根据商品条码查询商品
     * @param goodsCode
     * @return
     */
    Goods findByGoodsCode(String goodsCode);


    /**
     * 根据商品条码查询库存中的商品
     * @param goodsCode
     * @return
     */
    GoodsVo selGoodsAndInventoryDetailByGoodsCode(String goodsCode);

    /**
     *  添加商品
     * @param goods
     * @return
     */
    int addGoods(Goods goods);

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
    int batchModify(@Param("ids") int[] ids, @Param("goodsTypeId") int goodsTypeId);

    /**
     *  根据id查询商品信息
     * @param id
     * @return
     */
    List<GoodsVo> findById(Integer id,Integer providerId);

    /**
     *  导出商品信息
     */
    void exportGoods(OutputStream outputStream);

    /**
     * 根据供应商id查询商品信息
     * @param providerId
     * @return
     */
    List<Goods> selGoodsByProviderId(Integer providerId);
}
