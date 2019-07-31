package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.OrderDetail;
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

    /**
     *  查询批发订单总金额-----------------------------
     * @return
     */
    @ResponseBody
    @PostMapping("/findWholeTotalMoney")
    public String findWholeTotalMoney(){
        return JSON.toJSONString(orderDetailService.findWholeTotalMoney());
    }

    /**
     * ---------------------------------
     *  根据订单明细id删除订单明细
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteById")
    public String deleteById(int id){
        int rowCount = orderDetailService.deleteById(id);
        Map<String,Object> map = new HashMap<>();
        if (rowCount > 0){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     *  添加批发订单明细
     * @param orderDetail
     * @return
     */
    @ResponseBody
    @PostMapping("/addWholeOrder")
    public String addWholeOrder(OrderDetail orderDetail){
        int rowCount = orderDetailService.addWholeOrder(orderDetail);
        Map<String,Object> map = new HashMap<>();
        if (rowCount > 0){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     *  修改批发订单明细
     * @param orderDetail
     * @return
     */
    @ResponseBody
    @PostMapping("/updateWholeOrder")
    public String updateWholeOrder(OrderDetail orderDetail){
        int rowCount = orderDetailService.updateWholeOrder(orderDetail);
        Map<String,Object> map = new HashMap<>();
        if (rowCount > 0){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }
}

