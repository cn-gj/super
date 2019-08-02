package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.dao.ProShopRelationMapper;
import com.superman.supermarket.entity.ProShopRelation;
import com.superman.supermarket.service.ProShopRelationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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
@RequestMapping("/proShopRelation")
public class ProShopRelationController {

    @Resource
    private ProShopRelationService proShopRelationService;

    /**
     * 往中间表添加门店和供应商信息
     * @param proShopRelation
     * @return
     */
   @ResponseBody
   @PostMapping("/addProShop")
    public String addProShop(ProShopRelation proShopRelation) {
       Map<String,Object> map = new HashMap<>();
       if (proShopRelationService.addProShop(proShopRelation) > 0){
           map.put("state",true);//成功
       }else {
           map.put("state",false);
       }
       return JSON.toJSONString(map);
    }
}

