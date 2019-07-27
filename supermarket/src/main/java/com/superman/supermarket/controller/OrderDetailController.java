package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.dao.OrderDetailMapper;
import com.superman.supermarket.entity.OrderDetail;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Resource
    private OrderDetailMapper orderDetailMapper;

   @GetMapping("/findAll")
   @ResponseBody
    public String findAll(){
       List<OrderDetail> details = orderDetailMapper.selectList(null);
        return JSON.toJSONString(details);
    }
}

