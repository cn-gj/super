package com.superman.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.Customer;
import com.superman.supermarket.entity.vo.CustomerVo;
import org.apache.ibatis.annotations.Param;

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
     * 批量删除
     * */
    int batchDelete(int[] ids);

    /**
     * 批量修改商品类型
     */
    int batchModify(@Param("ids") int[] ids, @Param("id") int id);

    /**
     * 根据ID修改状态
     **/
    int batchModifyCustomerstatus(@Param("id") int id, @Param("customerStatus") int customerStatus);

    /**
     * 添加客户列表
     * */
    int addCustomerstatus(CustomerVo customerVo);

    /*
     * 获取客户名称
     * */
    List<CustomerVo> findByName(int id);
}
