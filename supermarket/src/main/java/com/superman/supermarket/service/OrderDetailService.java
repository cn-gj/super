package com.superman.supermarket.service;

import com.superman.supermarket.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.vo.OrderDetailVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface OrderDetailService extends IService<OrderDetail> {

    /**
     * 根据订单号查询订单详情
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
     * --------------------------------------------------
     *  根据明细id删除明细
     * @param id
     * @return
     */
    int deleteById(int id);

    /**
     * ------------------------------------
     * 添加批发订单明细
     * @param orderDetail
     * @return
     */
    int addWholeOrder(OrderDetail orderDetail);

    /**
     * ------------------------------------
     *  修改批发订单明细
     * @param orderDetail
     * @return
     */
    int updateWholeOrder(OrderDetail orderDetail);

}
