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
     * 多条件查询订单或查询所有
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
     *  导出订单信息Execl
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

