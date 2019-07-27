package com.superman.supermarket.service;

import com.superman.supermarket.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.OrderDetail;
import com.superman.supermarket.entity.vo.OrderVo;

import java.io.OutputStream;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface OrderService extends IService<Order> {
    /**
     *  查询订单信息或多条件查询
     * @return
     */
    List<OrderVo> findAllOrderOrCondition(OrderVo orderVo);

    /**
     * 下载订单列表
     * @param outputStream
     */
    public void exportOrder(OutputStream outputStream);

    /**
     * 生成订单表
     * 选择商品后，生成订单明细表
     * @param order
     * @return
     */
    Integer addOrder(Order order,OrderDetail orderDetail);

}
