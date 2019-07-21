package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Spec;
import com.superman.supermarket.entity.SpecDetail;
import com.superman.supermarket.service.SpecDetailService;
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
@RequestMapping("/specDetail")
public class SpecDetailController {
    @Resource
    private SpecDetailService specDetailService;

    @ResponseBody
    @PostMapping("/addSpecDetail")
    public String addSpecDetail(SpecDetail specDetail){
        Map<String,Object> map = new HashMap<>();
        Integer count = specDetailService.addSpecDetail(specDetail);
        if (count > 0){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除规格详情
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/delSpecDetail")
    public String deleteSpecDetail(Integer id){
        Map<String,Object> map = new HashMap<>();
        Integer count = specDetailService.deleteSpecDetail(id);
        if (count > 0){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }
}

