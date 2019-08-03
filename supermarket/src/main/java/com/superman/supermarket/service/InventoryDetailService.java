package com.superman.supermarket.service;

import com.superman.supermarket.entity.InventoryDetail;
import com.baomidou.mybatisplus.extension.service.IService;
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

}
