package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.superman.supermarket.entity.OrderDetail;
import com.superman.supermarket.entity.WholeOrder;
import com.superman.supermarket.entity.vo.WholeOrderVo;
import com.superman.supermarket.service.WholeOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
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
@RequestMapping("/wholeOrder")
public class WholeOrderController {

    @Resource
    private WholeOrderService wholeOrderService;

    /**
     *  添加批发订单
     * @param
     * @return
     */
    @PostMapping("/addWholeOrder")
    @ResponseBody
    public String addWholeOrder(@RequestBody String str ){
        Map<String,Object> map = new HashMap<>();
        map.put("result",wholeOrderService.addWholeOrder(str));
        return JSON.toJSONString(map);
    }

    /**
     *  根据条件查询批发订单
     * @param wholeOrderVo
     * @return
     */
    @PostMapping("/findByCondition")
    @ResponseBody
    public String findByCondition(WholeOrderVo wholeOrderVo){
        return JSON.toJSONString(wholeOrderService.findByCondition(wholeOrderVo));
    }


    /**
     *  根据订单id查询订单详情
     * @param id
     * @return
     */
    @PostMapping("/findById")
    @ResponseBody
    public String findById(int id){
        return JSON.toJSONString(wholeOrderService.findById(id));
    }

}

