package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.OrderDetail;
import com.superman.supermarket.dao.OrderDetailMapper;
import com.superman.supermarket.entity.vo.OrderDetailVo;
import com.superman.supermarket.entity.vo.ShopVo;
import com.superman.supermarket.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.utils.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
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

    @Override
    public List<OrderDetailVo> findOrderDateilByOrderId(Integer id) {
        return orderDetailMapper.findOrderDateilByOrderId(id);
    }

}
