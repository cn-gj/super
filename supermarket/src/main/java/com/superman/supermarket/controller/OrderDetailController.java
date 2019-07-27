package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.vo.OrderDetailVo;
import com.superman.supermarket.service.OrderDetailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Resource
    private OrderDetailService orderDetailService;

    @ResponseBody
    @PostMapping("/getOdById")
    public String findOrderDetailByOrderId(Integer id){
        Map<String,Object> map = new HashMap<>();
        List<OrderDetailVo> orderDetailVoList = orderDetailService.findOrderDateilByOrderId(id);
        map.put("orderDetailList",orderDetailVoList);
        return JSON.toJSONString(map);
    }
}

