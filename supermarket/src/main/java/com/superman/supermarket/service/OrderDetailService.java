package com.superman.supermarket.service;

import com.superman.supermarket.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.vo.OrderDetailVo;

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
public interface OrderDetailService extends IService<OrderDetail> {

    /**
     * 根据订单号查询订单详情
     * @param id
     * @return
     */
    public List<OrderDetailVo> findOrderDateilByOrderId(Integer id);

}
