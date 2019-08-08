package com.superman.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.InventoryDetail;
import com.superman.supermarket.entity.vo.InventoryDetailVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface InventoryDetailService extends IService<InventoryDetail> {

    /**
     * 根据采购收获商品数量修改或者添加库存明细
     * @param goodsStr
     * @return
     */
    Boolean updateInventoryByOrderDetail(Integer storeId,String goodsStr);

    /**
     * 根据采购退货单的商品信息修改库存明细
     * 根据商品id和库存id批量修改库存明细
     * @param storeId
     * @param goodsStr
     * @return
     */
    Boolean updateInventoryByOrderDetailReturn(Integer storeId,String goodsStr);

    /**
     * 根据仓库id查询仓库下的商品信息
     * @param storeId
     * @return
     */
    List<InventoryDetail> selGoodsByStoreId(Integer storeId);
    /**
     *  根据仓库id查询该仓库下的库存明细
     * @param inventoryDetailVo
     * @return
     */
    List<InventoryDetailVo> findByStoreId(InventoryDetailVo inventoryDetailVo);



    /**------------------------------------------
     *  根据仓库id查询该仓库下的库存明细
     * @param storeId
     * @return
     */
    List<InventoryDetailVo> selectByStoreId(int storeId);

    /**
     *根据仓库id查询仓库、库存明细、商品、门店信息
     * @param storeId
     * @return
     */
    List<InventoryDetailVo> selInventoryDetailByStoreId(Integer storeId);

    /**
     * 查询仓库中商品总数量
     * @param storeId
     * @return
     */
    Integer selInventoryDetailCount(Integer storeId);
}
