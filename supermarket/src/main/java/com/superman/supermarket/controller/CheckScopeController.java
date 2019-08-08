package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.service.CheckScopeService;
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
@RequestMapping("/checkScope")
public class CheckScopeController {
    @Resource
    public CheckScopeService checkScopeService;

    /**
     *  查询所有盘点类型
     * @return
     */
    @PostMapping("/findAll")
    @ResponseBody
    public String findAll(){
        return JSON.toJSONString(checkScopeService.findAll());
    }
}

