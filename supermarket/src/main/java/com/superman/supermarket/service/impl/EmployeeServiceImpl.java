package com.superman.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.superman.supermarket.entity.Employee;
import com.superman.supermarket.dao.EmployeeMapper;
import com.superman.supermarket.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.utils.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Service
@Transactional
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public Employee getEmployeeByEmpAccount(String account) {
        return employeeMapper.getEmployeeByEmpAccount(account);
    }

    @Override
    public List<Employee> findEmpByCondition(Employee employee) {
       return employeeMapper.findEmpByCondition(employee);
    }

    @Override
    public Employee findEmpById(int id) {
        return employeeMapper.findEmpById(id);
    }

    @Override
    public Map<String, Object> modifyEmpById(Employee employee) {
        Map<String,Object> map = new HashMap<>();
        int count = employeeMapper.modifyEmpById(employee);
        if (count > 0){
            map.put("result",true);
            map.put("message","修改成功!");
        }else {
            map.put("result",false);
            map.put("message","修改失败!");
        }
        return map;
    }

    @Override
    public Map<String,Object> addEmp(Employee employee) {
        Map<String,Object> map = new HashMap<>();
        // 判断用户名是否存在
        Employee emp = employeeMapper.getEmployeeByEmpAccount(employee.getEmpAccount());
        /**
         *  已存在
         */
        if (emp != null){
            map.put("result",false);
            map.put("message","用户名已存在!");
        }else {
            // 对员工密码进行加密处理
            /**
             * 	source:要加密的数据
             * 	salt:盐,扰乱码
             * 	hashIterations:加密次数
             */
            // 给员工密码进行加密
            Md5Hash md5Hash = new Md5Hash(employee.getEmpPwd(), employee.getEmpAccount(), 2);
            // 给员工设置加密后的密码
            employee.setEmpPwd(md5Hash.toString());
            int count = employeeMapper.addEmp(employee);
            if(count > 0){
                map.put("result",true);
                map.put("message","添加成功!");
            }else {
                map.put("result",false);
                map.put("message","添加失败!");
            }
        }
        return map;
    }

    @Override
    public void exportEmp(OutputStream outputStream) {
        // 获取员工信息列表
        List<Employee> emps = employeeMapper.findAll();
        // 表名称
        String sheetName = "店铺员工列表_"+new Date().toString();
        // 创建execl工作簿对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建execl表对象
        HSSFSheet sheet = workbook.createSheet();

        // 创建单元格样式对象
        HSSFCellStyle headCellStyle = workbook.createCellStyle();
        // 设置头部标题单元格水平居中
        headCellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置头部标题单元格垂直居中
        headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);


        // 创建字体样式对象
        HSSFFont headFont = workbook.createFont();
        headFont.setFontName("黑体");// 设置字体样式
        headFont.setBold(true); // 字体加粗
        headFont.setFontHeightInPoints((short)11);  // 设置字体大小

        // 给头部样式设置头部字体样式
        headCellStyle.setFont(headFont);

        // 头部标题
        String headTitle[] = {"归属门店","登录账号","员工名称","员工类型","员工状态","联系电话","创建时间"};
        // 创建标题行对象
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = null;
        // 遍历标题内容到表格中
        for (int i = 0; i < headTitle.length; i++){
            // 创建单元格,并赋值
            titleCell = titleRow.createCell(i);
            titleCell.setCellValue(headTitle[i]);
            // 给头部单元格设置样式
            titleCell.setCellStyle(headCellStyle);
            // 给列设置宽度
            sheet.setColumnWidth(i,8000);
        }


        HSSFRow contentRow = null;  // 内容行对象
        Integer rowIndex = 1;   // 内容行起始索引
        // 写入员工信息到表格中
        for (Employee emp : emps) {
            // 创建行
            contentRow = sheet.createRow(rowIndex);
            // 创建单元格,赋值
            // 所属店铺
            contentRow.createCell(0).setCellValue(emp.getShop().getShopName());
            // 登录账号
            contentRow.createCell(1).setCellValue(emp.getEmpAccount());
            // 员工名称
            contentRow.createCell(2).setCellValue(emp.getEmpName());
            // 判断用户角色是否为空
            if(emp.getRoles() != null && emp.getRoles().getRoleName() != null){
                // 员工角色
                contentRow.createCell(3).setCellValue(emp.getRoles().getRoleName());
            }else {
                contentRow.createCell(3).setCellValue("null");
            }
            // 员工状态
            contentRow.createCell(4).setCellValue(empStauts2String(emp.getEmpStatus()));
            // 联系电话
            contentRow.createCell(5).setCellValue(emp.getEmpPhone());
            // 创建时间
            contentRow.createCell(6).setCellValue(DateUtil.date2Str(emp.getCreateDate()));

            rowIndex++;
        }

        try {
            // 将数据写入流中
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                // 关闭工作簿
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }


    /**
     *  根据员工状态转字符串
     * @param empStauts
     * @return
     */
    private String empStauts2String(int empStauts){
        String stauts = "";
       switch (empStauts){
           case 0:
               stauts = "正常";
               break;
           case 1:
               stauts = "禁用";
               break;
           case 2:
               stauts = "离职";
               break;
           default:
               stauts = "暂无状态";
       }
       return stauts;
    }
}
