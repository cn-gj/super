package com.superman.supermarket.dao;

import com.superman.supermarket.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.vo.OrderDetailVo;

import java.util.List;

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
     * 根据订单号查询订单详情/********************************
     * @param id
     * @return
     */
    public List<OrderDetailVo> findOrderDateilByOrderId(Integer id);

    /**
     *  添加订单明细,前台需要区分是采购订单还是批发订单
     * @param orderDetail
     * @return
     */
    int addOrderDetail(OrderDetail orderDetail);
}
