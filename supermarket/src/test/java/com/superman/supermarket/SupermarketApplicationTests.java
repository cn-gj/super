package com.superman.supermarket;


import com.superman.supermarket.dao.ShopMapper;
import com.superman.supermarket.entity.Employee;
import com.superman.supermarket.entity.Roles;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.entity.vo.ShopVO;
import com.superman.supermarket.service.ShopService;

import com.superman.supermarket.dao.EmployeeMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupermarketApplicationTests {
    @Resource
    private ShopService shopService;
    @Resource
    private ShopMapper shopMapper;

    @Resource
    private EmployeeMapper employeeMapper;
    @Test
    public void contextLoads() {
       //System.out.print(employeeMapper.findAll());
       // Employee employee = new Employee();
/*        Roles roles = new Roles();
        roles.setId(1);
        employee.setRoles(roles);
        employee.setEmpStatus(1);*/
        /*Shop shop = new Shop();
        shop.setId(1);
        employee.setShop(shop);
        employee.setEmpAccount("admin");*/
        //employee.setEmpName("爱的米");
        //employee.setEmpPhone("110");
       List<Employee> emps = employeeMapper.findAll();
       System.out.print(emps.size()+"........");
        /*for (Employee emp: emps) {
            System.out.println("所属店铺"+emp.getShop());
            System.out.println("登录账号"+emp.getEmpAccount());
            System.out.println("名称"+emp.getEmpName());
            System.out.println("类型"+emp.getRoles().getRoleName());
            System.out.println("状态"+emp.getEmpStatus());
            System.out.println("联系电话"+emp.getEmpPhone());
            System.out.println("创建时间"+emp.getCreateDate());
        }*/
    }

}
