package com.superman.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.dao.CustEmpRelationMapper;
import com.superman.supermarket.dao.CustomerMapper;
import com.superman.supermarket.entity.CustEmpRelation;
import com.superman.supermarket.entity.Customer;
import com.superman.supermarket.entity.vo.CustomerVo;
import com.superman.supermarket.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private CustEmpRelationMapper custEmpRelationMapper;
    @Override
    public List<CustomerVo> findByList(CustomerVo customerVo) {
        return customerMapper.findByList(customerVo);
    }

    @Override
    public List<CustomerVo> findByListCust(int id) {
        return customerMapper.findByListCust(id);
    }

    @Override
    public List<CustomerVo> findByMessage(CustomerVo customerVo) {
        return customerMapper.findByMessage(customerVo);
    }

    @Override
    public int batchDelete(int[] ids) {
        return customerMapper.batchDelete(ids);
    }

    /**
     * 修改客户信息
     * @param customer
     * @return
     */
    @Override
    public Integer batchCust(Customer customer) {
        return customerMapper.batchCust(customer);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public int addCustomerstatus(CustomerVo customerVo) {
        int rowCount = customerMapper.addCustomerstatus(customerVo);
        // 为了保证数据的准确性 添加一个客户，就要添加一个客户与所属员工的关系
        CustEmpRelation custEmpRelation = new CustEmpRelation();
        custEmpRelation.setCustomerId(customerVo.getId());
        custEmpRelation.setEmpId(customerVo.getEmpId());
        rowCount += custEmpRelationMapper.insert(custEmpRelation);
        return rowCount;
    }

    @Override
    public List<CustomerVo> findByName(int id) {
        return customerMapper.findByName(id);
    }

    @Override
    public Customer findByCustomerName(String name) {
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_name",name);
        List<Customer> customers = customerMapper.selectList(wrapper);
        if (customers != null && customers.size() > 0){
            return customers.get(0);
        }
        return null;
    }
}
