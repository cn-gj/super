package com.superman.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.InventoryCheck;
import com.superman.supermarket.entity.vo.InventoryCheckVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface InventoryCheckService extends IService<InventoryCheck> {

    /**
     *  添加库存盘点
     * @param inventoryCheckVo
     * @return
     */
    Boolean addInvCheck(InventoryCheckVo inventoryCheckVo);

    /**
     *  初始化和根据条件查询库存盘点清单
     * @param inventoryCheckVo
     * @return
     */
    List<InventoryCheckVo> findByCondition(InventoryCheckVo inventoryCheckVo);

    /**
     *  根据盘点单的id查询盘点单信息
     * @param invId
     * @return
     */
    InventoryCheckVo findById(int invId);

    /**
     *  修改库存盘点单
     * @param inventoryCheckVo
     * @return
     */
    int updateInventoryCheck(InventoryCheckVo inventoryCheckVo);









    // ---------------------------------------------

    /**
     *  添加库存盘点单并添加库存盘点明细
     * @param jsonStr
     * @return
     */
    boolean addCheck(InventoryCheckVo inventoryCheckVo,String jsonStr);

}
