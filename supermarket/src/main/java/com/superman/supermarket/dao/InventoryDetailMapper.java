package com.superman.supermarket.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.InventoryDetail;
import com.superman.supermarket.entity.TicketDetail;
import com.superman.supermarket.entity.vo.InventoryDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface InventoryDetailMapper extends BaseMapper<InventoryDetail> {

    /**
     *  根据商品id查询库存信息中有的商品id
     * @param ids
     * @return
     */
    Integer[] findGoodsIdByIds(@Param("ids")int[] ids,@Param("storeId") int storeId);


    /**
     *  根据仓库id和商品id查询库存明细中的商品数量
     * @param storeId
     * @param goodsId
     * @return
     */
    int findGoodsCountById(@Param("storeId")int storeId,@Param("goodsId") int goodsId);

    /**
     *  根据仓库id和库存商品id修改商品库存数量
     * @param storeId
     * @param goodsId
     * @param goodsCount
     * @return
     */
    int updateInvByGoodsIds(@Param("storeId")int storeId,@Param("goodsId") int goodsId,@Param("goodsCount")int goodsCount);

    /**
     * 根据小票明细同步库存明细
     * @param detailList
     * @return
     */
    int updateInvByTicketDetailList(@Param("detailList") List<TicketDetail> detailList);



    /*
     * 添加商品库存明细(供应商收货添加)
     * @param detailList
     * @return
     */
    Integer insertInventoryDetailByProviderTake(@Param("orderDetailMapList") List<Map<String,Object>> orderDetailMapList);

    /**
     * 根据采购收获商品数量修改库存明细
     * @param orderDetailMapList
     * @return
     */
    Integer updateInventoryByOrderDetail(@Param("orderDetailMapList") List<Map<String,Object>> orderDetailMapList);

    /**
     * 根据仓库id查询仓库下的商品信息
     * @param storeId
     * @return
     */
    List<InventoryDetail> selInventoryByStoreId(Integer storeId);

    /**
     * 根据采购退货单的商品信息修改库存明细
     * @param orderDetailMapList
     * @return
     */
    Integer updateInventoryByOrderDetailReturn(@Param("orderDetailMapList") List<Map<String,Object>> orderDetailMapList);

    /**
     * 根据仓库id查询商品信息
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



    // -------------------------------------------
    /**------------------------------------------
     *  根据仓库id查询该仓库下的库存明细
     * @param storeId
     * @return
     */
    List<InventoryDetailVo> selectByStoreId(int storeId);


    /**
     *根据仓库id查询仓库、库存明细、商品、门店信息-
     * @param storeId
     * @return
     */
    List<InventoryDetailVo> selInventoryDetailByStoreId(@Param("storeId") Integer storeId);

    /**
     * 查询仓库中商品总数量
     * @param storeId
     * @return
     */
    Integer selInventoryDetailCount(Integer storeId);

    /**
     *  根据库存id修改库存数量
     * @param id
     * @return
     */
    int updateDetailById(int id);

}
