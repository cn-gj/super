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
     * ------------------------------------------
     *  批发订单
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

    /**
     * -----------------------------------------
     *  根据批发订单id获取批发订单金额
     * @param wholeId
     * @return
     */
    Double findWholeMoney(int wholeId);


    /**
     * 根据订单号查询订单详情/********************************
     * @param id
     * @return
     */
    public List<OrderDetailVo> findOrderDateilByOrderId(Integer id);


    /**
     * ------------------------------------------------------
     *  查询批发订单总金额
     * @return
     */
    Double findWholeTotalMoney();

    /**
     * --------------------------------------------
     *  根据批发订单id查询该订单下的订单明细
     * @param id
     * @return
     */
    List<OrderDetailVo> findByWholeId(int id);

    /**
     * --------------------------------------------------
     *  根据明细id删除明细
     * @param id
     * @return
     */
    int deleteById(int id);


    // 添加明细
}
