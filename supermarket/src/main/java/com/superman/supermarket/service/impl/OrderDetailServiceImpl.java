package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.OrderDetail;
import com.superman.supermarket.dao.OrderDetailMapper;
import com.superman.supermarket.entity.vo.OrderDetailVo;
import com.superman.supermarket.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Resource
    private OrderDetailMapper orderDetailMapper;


    /**
     *根据订单号查询订单详情/********************************
     * @param id
     * @return
     */
    @Override
    public List<OrderDetailVo> findOrderDateilByOrderId(Integer id) {
        return orderDetailMapper.findOrderDateilByOrderId(id);
    }

    @Override
    public Double findWholeTotalMoney() {
        return orderDetailMapper.findWholeTotalMoney();
    }

    @Override
    public int deleteById(int id) {
        return orderDetailMapper.deleteById(id);
    }

    @Override
    public int addWholeOrder(OrderDetail orderDetail) {
        return orderDetailMapper.addOrderDetail(orderDetail);
    }

    @Override
    public int updateWholeOrder(OrderDetail orderDetail) {
        return orderDetailMapper.updateWholeOrder(orderDetail);
    }
}
