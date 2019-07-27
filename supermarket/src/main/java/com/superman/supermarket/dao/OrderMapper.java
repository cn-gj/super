package com.superman.supermarket.dao;

import com.superman.supermarket.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.OrderDetail;
import com.superman.supermarket.entity.vo.OrderVo;
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
public interface OrderMapper extends BaseMapper<Order> {

    /**
     *  查询订单信息或多条件查询
     * @return
     */
    List<OrderVo> findAllOrderOrCondition(@Param("orderVo") OrderVo orderVo);

    /**
     * 生成订单表
     * @param order
     * @return
     */
    Integer addOrder(Order order);

    /**
     * 选择商品后，生成订单明细表
     * @param orderDetail
     * @return
     */
    Integer addOrderDetail(OrderDetail orderDetail);
}
