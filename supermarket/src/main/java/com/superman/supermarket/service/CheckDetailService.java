package com.superman.supermarket.service;

import com.superman.supermarket.entity.CheckDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.vo.CheckDetailVo;
import com.superman.supermarket.entity.vo.InventoryDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface CheckDetailService extends IService<CheckDetail> {

    /**
     *  根据库存明细生成库存盘点明细
     * @param inventoryDetailVo
     * @return
     */
    CheckDetailVo creatByInvDetailId(InventoryDetailVo inventoryDetailVo,int inCheckId);
}
