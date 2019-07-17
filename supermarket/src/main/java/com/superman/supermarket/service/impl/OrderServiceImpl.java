package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.Order;
import com.superman.supermarket.dao.OrderMapper;
import com.superman.supermarket.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
