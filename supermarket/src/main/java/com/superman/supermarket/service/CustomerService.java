package com.superman.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.Customer;
import com.superman.supermarket.entity.vo.CustomerVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface CustomerService extends IService<Customer> {
    /**
     *  根据条件查询所有客户列表
     */
    List<CustomerVo> findByList(CustomerVo customerVo);

    /**
     * 点击详情查询客户信息
     * */
    List<CustomerVo> findByListCust(int id);

    /*
     * 查询客户信息
     * */
    List<CustomerVo> findByMessage(CustomerVo customerVo);

    /**
     * 修改客户信息
     * @return
     */
    Integer batchCust(Customer customer);

    /**
     * 批量删除
     * */
    int batchDelete(int[] ids);

    /**
     * 添加客户列表
     * */
    int addCustomerstatus(CustomerVo customerVo);

    /*
     * 获取客户名称
     * */
    List<CustomerVo> findByName(int id);

    /**
     *  根据客户名称查询客户信息
     * @param name
     * @return
     */
    Customer findByCustomerName(String name);
}
