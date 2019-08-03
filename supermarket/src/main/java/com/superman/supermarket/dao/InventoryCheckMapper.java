package com.superman.supermarket.dao;

import com.superman.supermarket.entity.InventoryCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.vo.InventoryCheckVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface InventoryCheckMapper extends BaseMapper<InventoryCheck> {

    /**
     *  添加库存盘点
     * @param inventoryCheckVo
     * @return
     */
    int addInvCheck(InventoryCheckVo inventoryCheckVo);

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
}
