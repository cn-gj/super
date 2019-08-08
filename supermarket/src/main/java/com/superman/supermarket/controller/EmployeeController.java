package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Employee;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.service.EmployeeService;
import com.superman.supermarket.utils.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @ResponseBody
    @PostMapping("/upPwd")
    public String updateEmpPwdByEmpId(Employee employee){
        Map<String,Object> map = new HashMap<>();
        //给密码加密
        /**
         * 	source:要加密的数据
         * 	salt:盐,扰乱码
         * 	hashIterations:加密次数
         */
        // 给员工密码进行加密
        Md5Hash md5Hash = new Md5Hash(employee.getEmpPwd(), employee.getEmpAccount(), 2);
        // 给员工设置加密后的密码
        employee.setEmpPwd(md5Hash.toString());
        Integer count = employeeService.updateEmpPwdByEmpId(employee);
        if (count > 0){
            map.put("state",true);//成功
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     *      登录请求
     * @param employee
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public String login(Employee employee,HttpSession session) {
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
            map.put("shopId",emp.getShopId());
            // 保存登录对象返回前台
            map.put("empId",emp.getId());
            // 将当前登录用户保存到session中
            if (session.getAttribute("currentEmp") == null){
                session.setAttribute("currentEmp",emp);
            }else {
                if (session.getAttribute("currentEmp") != emp){
                    session.setAttribute("currentEmp",emp);
                }
            }
        } catch (AuthenticationException e) {
            map.put("state",false);
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
    @GetMapping("/exportEmp")
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

    /**
     *  根据员工id查询员工门店信息
     * @param id
     * @return
     */
    @PostMapping("/selectshop")
    @ResponseBody
    public String selectfindshop(int id){
        Map<String,Object> map = new HashMap<>();
        List<Shop> list = employeeService.selectfindshop(id);
        map.put("shopListByEmpId",list.get(0));
        return JSON.toJSONString(map);
    }

    /**
     *  根据员工id修改门店信息
     * @return
     */
    @PostMapping("/updateshop")
    @ResponseBody
    public String updatefindshop(Shop shop){
        Map<String,Object> map = new HashMap<>();
        Integer count = employeeService.updatefindshop(shop);
        if (count > 0){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return  JSON.toJSONString(map);
    }


    /**
     * 退出
     * @return
     */
    @ResponseBody
    @PostMapping("/exit")
    public String exitUser(HttpSession session){
        Map<String,Object>map = new HashMap<>();
        try {
            session.removeAttribute("currentEmp");
            map.put("state",true);
        } catch (InvalidSessionException e) {
            e.printStackTrace();
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }
}

