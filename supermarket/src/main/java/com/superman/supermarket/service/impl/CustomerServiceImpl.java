package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.Customer;
import com.superman.supermarket.dao.CustomerMapper;
import com.superman.supermarket.service.CustomerService;
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
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
