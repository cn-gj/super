package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.vo.InventoryDetailVo;
import com.superman.supermarket.service.CheckDetailService;
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
@RequestMapping("/checkDetail")
public class CheckDetailController {
    @Resource
    private CheckDetailService checkDetailService;

    /**
     *  根据库存明细生成盘点明细
     * @param inventoryDetailVo
     * @param inCheckId
     * @return
     */
    @PostMapping("/creatByInvDetailId")
    @ResponseBody
    public String creatByInvDetailId(InventoryDetailVo inventoryDetailVo,int inCheckId){
        return JSON.toJSONString(checkDetailService.creatByInvDetailId(inventoryDetailVo,inCheckId));
    }
}

