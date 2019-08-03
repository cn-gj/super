package com.superman.supermarket.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.Customer;
import com.superman.supermarket.entity.vo.CustomerVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface CustomerMapper extends BaseMapper<Customer> {
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
     * 批量删除
     * */
    int batchDelete(int[] ids);

    /**
     * 修改客户信息
     * @return
     */
    Integer batchCust(Customer customer);

    /**
     * 添加客户列表
     * */
    int addCustomerstatus(CustomerVo customerVo);

    /*
    * 获取客户名称
    * */
    List<CustomerVo> findByName(int id);
}
