package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Spec;
import com.superman.supermarket.service.SpecService;
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
@RequestMapping("/spec")
public class SpecController {

    @Resource
    private SpecService specService;

    /**
     * 修改规格
     * @param spec
     * @return
     */
    @ResponseBody
    @PostMapping("/upSpec")
    public String updateSpec(Spec spec){
        Map<String,Object> map = new HashMap<>();
        Integer count = specService.updateSpec(spec);
        if (count > 0){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }
}

