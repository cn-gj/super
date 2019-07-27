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
  /*  @PostMapping("/addWholeOrder")
    @ResponseBody*/
   /* public String addWholeOrder(WholeOrderVo wholeOrderVo){
        System.out.println(wholeOrderVo.getJsonSrt()+".....");
        // 将json格式的字符串装换成list集合
        wholeOrderVo.setDetails(JSON.parseArray(wholeOrderVo.getJsonSrt(), OrderDetail.class));
        Map<String,Object> map = new HashMap<>();
        return JSON.toJSONString(map.put("result",wholeOrderService.addWholeOrder(wholeOrderVo)));
    }*/

    @PostMapping("/addWholeOrder")
    @ResponseBody
    public String addWholeOrder(@RequestBody String str ){
        Map<String,Object> map = new HashMap<>();
        map.put("result",wholeOrderService.addWholeOrder(str));
        return JSON.toJSONString(map);
    }

}

