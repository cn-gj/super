package com.superman.supermarket.dao;

import com.superman.supermarket.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     *  根据id查询用户
     * @param id
     * @return
     */
    Employee findEmpById(int id);

    /**
     *  根据用户名查询对象
     * @param account
     * @return
     */
    Employee getEmployeeByEmpAccount(@Param("account") String account);

    /**
     *  根据条件查询员工信息
     * @return
     */
    List<Employee> findEmpByCondition(Employee employee);


    /**
     *  添加员工
     * @param emp
     * @return
     */
    int addEmp(Employee emp);


    /**
     *  根据id修改员工状态
     */
    int modifyEmpById(Employee employee);

    /**
     *  查询所有员工信息列表
     * @return
     */
    List<Employee> findAll();

    /**
     *  根据员工id查询门店信息
     * @return
     */
    List<Shop> selectfindshop(int id);

    /**
     *  修改门店信息
     * @return
     */
    Integer updatefindshop(Shop shop);

    /**
     * 修改登录账号的密码
     * @param employee
     * @return
     */
    Integer updateEmpPwdByEmpId(Employee employee);
}
