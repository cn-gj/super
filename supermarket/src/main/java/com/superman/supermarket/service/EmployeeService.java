package com.superman.supermarket.service;

import com.superman.supermarket.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 根据员工账号查询员工信息
     * @param account
     * @return
     */
    Employee getEmployeeByEmpAccount(String account);


    /**
     *  根据id查询用户
     * @param id
     * @return
     */
    Employee findEmpById(int id);


    /**
     *  根据条件查询员工信息
     * @return
     */
    List<Employee> findEmpByCondition(Employee employee);


    /**
     *  添加员工
     * @param employee
     * @return
     */
    Map<String,Object> addEmp(Employee employee);


    /**
     *  根据id修改员工状态
     */
    Map<String,Object> modifyEmpById(Employee employee);

    /**
     *  导出员工数据表格
     * @param outputStream
     */
    void exportEmp(OutputStream outputStream);
}
