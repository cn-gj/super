package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.service.StoreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Controller
@RequestMapping("/store")
public class StoreController {
    @Resource
    private StoreService storeService;

    /**
     *  根据店铺id查询仓库
     * @param shopId
     * @return
     */
    @PostMapping("/findByShopId")
    @ResponseBody
    public String findByShopId(Integer shopId){
        return JSON.toJSONString(storeService.findByShopId(shopId));
    }
}

