package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.vo.OrderVo;
import com.superman.supermarket.service.OrderService;
import com.superman.supermarket.utils.DateUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 多条件查询订单或查询所有/*****************
     * @param orderVo
     * @return
     */
    @ResponseBody
    @PostMapping("/orderList")
    public String findAllOrderOrCondition(OrderVo orderVo){
        Map<String,Object> map = new HashMap<>();
        List<OrderVo> orderVoList = orderService.findAllOrderOrCondition(orderVo);
        map.put("orderVoList",orderVoList);
        return JSON.toJSONString(map);
    }

    /**
     * 根据订单id查询订单信息即订单详情
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/selOrderAndDetailByOrderId")
    public String selOrderAndOrderDetailByOrderId(Integer id){
        return JSON.toJSONString(orderService.selOrderAndOrderDetailByOrderId(id));
    }


    /**
     * 添加订单/********************
     * @param orderVo
     * @param str
     * @return
     */
    @ResponseBody
    @PostMapping("/insertOrder")
    public String insertOredrAndOrderDetail(OrderVo orderVo,String str){
        Map<String,Object> map = new HashMap<>();
        try {
            Integer count = orderService.addOrder(orderVo,str);
            map.put("state",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改采购订单状态
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/upSingleState")
    public String updateOrderSingleState(Integer id,Integer singleState){
        Map<String,Object> map = new HashMap<>();
        Integer count = orderService.updateOrderSingleState(id,singleState);
        if (count > 0){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改采购订单收货状态
     * @param orderId  订单id
     * @param takeState 订单收获状态
     * @param storeId   仓库id
     * @param goodsStr  商品明细
     * @return
     */
    @ResponseBody
    @PostMapping("/upTakeState")
    public String updateTakeState(Integer orderId,Integer takeState,Integer storeId,String goodsStr){
        Map<String,Object> map = new HashMap<>();
        try {
           orderService.updateTakeState(orderId,takeState,storeId,goodsStr);
            map.put("state",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改采购订单收货状态
     * @param orderId  订单id
     * @param takeState 订单收获状态
     * @param storeId   仓库id
     * @param goodsStr  商品明细
     * @return
     */
    @ResponseBody
    @PostMapping("/upOrderTakeState")
    public String updateTakeStateReturn(Integer orderId,Integer takeState,Integer storeId,String goodsStr){
        Map<String,Object> map = new HashMap<>();
        try {
            Boolean flag =orderService.updateOrderTackState(orderId,takeState,storeId,goodsStr);
            map.put("state",flag);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除订单同时删除订单详情
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("delOrder")
    public String deleteOrder(Integer id){
        Map<String,Object> map = new HashMap<>();
        try {
            orderService.deleteOrder(id);
            map.put("state",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     *  导出采购订单信息Execl/*******************88
     * @return
     */
    @GetMapping("/exportOrder")
    public String exportEmp(HttpServletResponse response){
        String fileName = "订单信息列表_"+ DateUtil.date2Str(new Date()) +".xls";
        //中文名称进行转码
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(fileName.getBytes(),"ISO-8859-1"));
            // 调用业务层导出excel表格
            try {
                orderService.exportOrder(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  null;
    }
}

