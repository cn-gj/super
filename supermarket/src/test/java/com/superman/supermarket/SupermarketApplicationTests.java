package com.superman.supermarket;


import com.superman.supermarket.dao.EmployeeMapper;
import com.superman.supermarket.dao.ShopMapper;
import com.superman.supermarket.dao.TicketMapper;
import com.superman.supermarket.entity.Spec;
import com.superman.supermarket.entity.SpecTmp;
import com.superman.supermarket.entity.vo.TicketDetailVo;
import com.superman.supermarket.entity.vo.TicketVo;
import com.superman.supermarket.service.ShopService;
import com.superman.supermarket.service.SpecTmpService;
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
    private TicketMapper ticketMapper;

    @Resource
    private SpecTmpService specTmpService;

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
      /* List<Employee> emps = employeeMapper.findAll();
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




       /*String specs = "桌子,椅子,凳子";
        specTmpService.addSpecInfo(specs);*/


     /*  String idstr = "3,4,5,6,7";
       specTmpService.delSpecTmpInfo(idstr);*/


    }

}
