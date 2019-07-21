package com.superman.supermarket;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.superman.supermarket.dao.GoodsMapper;
import com.superman.supermarket.dao.ShopMapper;
import com.superman.supermarket.entity.Employee;
import com.superman.supermarket.entity.Goods;
import com.superman.supermarket.entity.Roles;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.entity.vo.ShopVO;
import com.superman.supermarket.service.GoodsService;
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
    private GoodsMapper goodsMapper;

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
        //employee.setEmpName("������");
        //employee.setEmpPhone("110");
       /*List<Employee> emps = employeeMapper.findAll();
       System.out.print(emps.size()+"........");*/
        /*for (Employee emp: emps) {
            System.out.println("��������"+emp.getShop());
            System.out.println("��¼�˺�"+emp.getEmpAccount());
            System.out.println("����"+emp.getEmpName());
            System.out.println("����"+emp.getRoles().getRoleName());
            System.out.println("״̬"+emp.getEmpStatus());
            System.out.println("��ϵ�绰"+emp.getEmpPhone());
            System.out.println("����ʱ��"+emp.getCreateDate());
        }*/

        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_name","洋乐多");
        List<Goods> list = goodsMapper.selectList(wrapper);
        System.out.print(list.size()+"...........................................");
    }

}
