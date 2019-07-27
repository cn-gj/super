package com.superman.supermarket.dao;

import com.superman.supermarket.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    /**
     *  添加订单明细,前台需要区分是采购订单还是批发订单
     * @param orderDetail
     * @return
     */
    int addOrderDetail(OrderDetail orderDetail);
}
