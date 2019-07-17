package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Employee;
import com.superman.supermarket.service.EmployeeService;
import com.superman.supermarket.utils.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;


    /**
     *      登录请求
     * @param employee
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public String login(Employee employee) {
       // System.out.println(employee);
        //用于接收返回页面的数据
        Map<String,Object> map = new HashMap<>();
        //获取当前对象
        Subject subject = SecurityUtils.getSubject();
        /**
         * 	source:要加密的数据
         * 	salt:盐,扰乱码
         * 	hashIterations:加密次数
         */
        // 给员工密码进行加密
        Md5Hash md5Hash = new Md5Hash(employee.getEmpPwd(), employee.getEmpAccount(), 2);
        // 给员工设置加密后的密码
        employee.setEmpPwd(md5Hash.toString());
        //获取令牌
        UsernamePasswordToken token = new UsernamePasswordToken(employee.getEmpAccount(),employee.getEmpPwd());
        try {
            subject.login(token);
            map.put("state",true);
            // 获取登录对象
            Employee emp = employeeService.getEmployeeByEmpAccount(employee.getEmpAccount());
            map.put("message","登录成功!");
            // 保存登录对象返回前台
            map.put("userCode",emp);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("message","用户名或密码错误!");
        }
        return JSON.toJSONString(map);
    }


    /**
     *  根据员工id查询员工信息
     * @param id
     * @return
     */
    @PostMapping("/findEmpById")
    @ResponseBody
    public String findEmpById(int id){
        return JSON.toJSONString(employeeService.findEmpById(id));
    }

    /**
     *  根据条件查询员工信息
     * @param employee
     * @return
     */
    @PostMapping("/empCondition")
    @ResponseBody
   public String findEmpByCondition(Employee employee){
        return JSON.toJSONString(employeeService.findEmpByCondition(employee));
   }


    /**
     *  根据用户名查询对象
     * @param account
     * @return
     */
    @PostMapping("/findEmpByName")
    @ResponseBody
   public String findEmpByName(String account){
        return JSON.toJSONString(employeeService.getEmployeeByEmpAccount(account));
   }


    /**
     *  添加员工
     * @param employee
     * @return
     */
    @PostMapping("/addEmp")
    @ResponseBody
   public String addEmp(Employee employee){
        return JSON.toJSONString(employeeService.addEmp(employee));
   }

    /**
     *  修改员工
     * @param employee
     * @return
     */
    @PostMapping("/modifyEmpById")
    @ResponseBody
   public String modifyEmpById(Employee employee){
        return JSON.toJSONString(employeeService.modifyEmpById(employee));
   }

    /**
     *  导出员工信息Execl
     * @return
     */
    @PostMapping("/exportEmp")
   public String exportEmp(HttpServletResponse response){
        String fileName = "店铺员工列表_"+ DateUtil.date2Str(new Date()) +".xls";
        //中文名称进行转码
       try {
           response.setHeader("Content-Disposition", "attachment;filename=" +
                  new String(fileName.getBytes(),"ISO-8859-1"));
           // 调用业务层导出excel表格
           try {
               employeeService.exportEmp(response.getOutputStream());
           } catch (IOException e) {
               e.printStackTrace();
           }
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }
       return  null;
   }
}

