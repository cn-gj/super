package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.vo.CustomerVo;
import com.superman.supermarket.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
@RequestMapping("/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    /**
     *  根据条件查询所有客户列表
     */
    @PostMapping("/findByList")
    @ResponseBody
    public String findByList(CustomerVo customerVo){
        return JSON.toJSONString(customerService.findByList(customerVo));
    }
    /**
     *  根据条件查询所有客户列表
     */
    @PostMapping("/findByListCust")
    @ResponseBody
    public String findByListCust(int id){
        return JSON.toJSONString(customerService.findByListCust(id));
    }

    /**
     *  查询客户信息
     */
    @PostMapping("/findByMessage")
    @ResponseBody
    public String findByMessage(CustomerVo customerVo){
        return JSON.toJSONString(customerService.findByMessage(customerVo));
    }

    /**
     *  批量删除商品请求
     */
    @PostMapping("/batchDelete")
    @ResponseBody
    public String batchDelete(int[] ids){
        int len = ids.length;
        Map<String,Object> map = new HashMap<>();
        if (customerService.batchDelete(ids) == len){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     *  批量修改商品类型请求
     */
    @PostMapping("/batchModify")
    @ResponseBody
    public  String batchModify(int[] ids,int id){
        int len = ids.length;
        Map<String,Object> map = new HashMap<>();
        if (customerService.batchModify(ids,id) == len){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     *  根据id修改状态
     */
    @PostMapping("/batchModifyCustomerstatus")
    @ResponseBody
    public String batchModifyCustomerstatus(int id , int customerStatus){
        Map<String,Object> map = new HashMap<String,Object>();
        int count = customerService.batchModifyCustomerstatus(id,customerStatus);
        if (count > 0){
            map.put("result",true);
        }else{
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }
    /**
     *添加客户列表
     **/
    @PostMapping("/addCustomerstatus")
    @ResponseBody
    public String addCustomerstatus(CustomerVo customerVo){
        Map<String,Object> map=new HashMap<>();
        int count =customerService.addCustomerstatus(customerVo);
        if (count >= 2){
            map.put("result",true);
        }else{
            map.put("result",false);
        }
        return  JSON.toJSONString(map);
    }

    /**
     *  获取客户名称
     */
    @PostMapping("/findByName")
    @ResponseBody
    public String findByName(int id) {
        return JSON.toJSONString(customerService.findByName(id));
    }
}

