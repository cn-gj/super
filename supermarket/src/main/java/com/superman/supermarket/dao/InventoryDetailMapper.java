package com.superman.supermarket.dao;

import com.superman.supermarket.entity.InventoryDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.TicketDetail;
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
}
