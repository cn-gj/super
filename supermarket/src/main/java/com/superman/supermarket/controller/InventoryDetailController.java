package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.vo.InventoryDetailVo;
import com.superman.supermarket.service.InventoryDetailService;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/inventoryDetail")
public class InventoryDetailController {
    @Resource
    private InventoryDetailService inventoryDetailService;

    @ResponseBody
    @PostMapping("/selGoodsByStoreId")
    public String selGoodsByStoreId(Integer storeId) {
        return JSON.toJSONString(inventoryDetailService.selGoodsByStoreId(storeId));
    }

    /**
     *  根据仓库id和条件查询该仓库下的库存明细
     * @param inventoryDetailVo
     * @return
     */
    @PostMapping("/findByStoreIdAndCondition")
    @ResponseBody
    public String findByStoreIdAndCondition(InventoryDetailVo inventoryDetailVo){
        return JSON.toJSONString(inventoryDetailService.findByStoreId(inventoryDetailVo));
    }


    //----------------------------------------------
    /**
     *  根据仓库id查询该仓库下的库存明细
     * @param storeId
     * @return
     */
    @PostMapping("/selectByStoreId")
    @ResponseBody
    public String selectByStoreId(int storeId){
        return JSON.toJSONString(inventoryDetailService.selectByStoreId(storeId));
    }

    /**
     *  根据仓库id和条件查询该仓库下的库存明细
     * @param storeId
     * @return
     */
    @PostMapping("/selInventoryDetailByStoreId")
    @ResponseBody
    public String selInventoryDetailByStoreId(Integer storeId){
        return JSON.toJSONString(inventoryDetailService.selInventoryDetailByStoreId(storeId));
    }
}

