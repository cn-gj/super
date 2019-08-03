package com.superman.supermarket.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.Order;
import com.superman.supermarket.entity.vo.OrderDetailVo;
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
    List<OrderVo> findAllOrderOrCondition(OrderVo orderVo);

    /**
     * 生成订单表
     * @param orderVo
     * @return
     */
    Integer addOrder(OrderVo orderVo);

    /**
     * 选择商品后，生成订单明细表
     * @param orderDetails
     * @return
     */
    Integer addOrderDetail(@Param("orderDetails") List<OrderDetailVo> orderDetails);

    /**
     * 修改订单的单据状态
     * @param id
     * @return
     */
    Integer updateOrderSingleState(@Param("id")Integer id,@Param("singleState") Integer singleState);

    /**
     * 删除订单的同时删除订单详情
     * @param id
     * @return
     */
    Integer deleteOrder(Integer id);

    /**
     * 修改收获状态
     * @param id
     * @return
     */
    Integer updateTakeState(@Param("takeState") Integer takeState,@Param("id") Integer  id);

    /**
     * 根据订单id查询订单信息即订单详情
     * @param id
     * @return
     */
    List<OrderVo> selOrderAndOrderDetailByOrderId(Integer id);
}
