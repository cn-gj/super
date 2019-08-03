package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.service.InventoryDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/inventoryDetail")
public class InventoryDetailController {

    @Resource
    private InventoryDetailService inventoryDetailService;

    @ResponseBody
    @PostMapping("/selGoodsByStoreId")
    public String selGoodsByStoreId(Integer storeId) {
        return JSON.toJSONString(inventoryDetailService.selGoodsByStoreId(storeId));
    }
}

