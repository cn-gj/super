package com.superman.supermarket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.superman.supermarket.service.ShopSummaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 *  报表控制器
 */
@Controller
@RequestMapping("shopSummary")
public class ShopSummaryController {
    @Resource
    private ShopSummaryService shopSummaryService;

    /**
     * 店铺汇总
     * @return
     */
    @PostMapping("/shopSummaryList")
    @ResponseBody
    public String shopSummary(){
        return JSON.toJSONString(shopSummaryService.shopSummary());
    }

    /**
     * 统计热销商品
     * @param limit
     * @return
     */
    @ResponseBody
    @PostMapping("/selGoodsSellCake")
    public String selGoodsSellCake(Integer limit){
        return JSONObject.toJSONString(shopSummaryService.selGoodsSellCake(limit));
    }
}
