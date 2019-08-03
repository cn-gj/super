package com.superman.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.Order;
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
     * @param orderVo
     * @return
     */
    public Integer addOrder(OrderVo orderVo,String str);

    /**
     * 修改订单的单据状态
     * @param id
     * @return
     */
    Integer updateOrderSingleState(Integer id,Integer singleState);

    /**
     * 删除订单的同时删除订单详情
     * @param id
     * @return
     */
    Integer deleteOrder(Integer id);

    /**
     * 修改收货状态
     * @param orderId 采购订单id
     * @param takeState 采购订单收货状态
     * @param storeId 仓库id
     * @param goodsStr  商品明细JSON
     * @return
     */
    boolean updateTakeState(Integer orderId,Integer takeState,Integer storeId,String goodsStr);

    /**
     * 根据订单id查询订单信息即订单详情
     * @param id
     * @return
     */
    List<OrderVo> selOrderAndOrderDetailByOrderId(Integer id);


    /**
     * 修改订单的退货状态
     * @return
     */
    Boolean updateOrderTackState(Integer orderId,Integer storeId,Integer tackState,String goodsStr);
}
