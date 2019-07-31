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
@RequestMapping("/specDetail")
public class SpecDetailController {
    @Resource
    private SpecDetailService specDetailService;

    /**
     * 添加规格详情
     * @param specDetail
     * @return
     */
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
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/delSpecDetail")
    public String deleteSpecDetail(int [] ids){
        Map<String,Object> map = new HashMap<>();
        Integer count = specDetailService.deleteSpecDetail(ids);
        if (count > 0){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 根据规格id查询规格详情
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/selSpecDetailByspecId")
    public String selectSpecDetailBySpecId(Integer id){
        Map<String,Object> map = new HashMap<>();
        List<SpecDetail> specDetailList = specDetailService.selectSpecDetailBySpecId(id);
        if (specDetailList != null){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }


    /**
     * 根据规格详情id查询商品信息
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/selGoodsBySpecDetailId")
    public String selectGoodsBySpecDetailId(int [] ids){
        Map<String,Object> map = new HashMap<>();
        Long count = specDetailService.selectGoodsBySpecDetailId(ids);
        if (count > 0){
            map.put("state",false);
        }else {
            map.put("state",true);
        }
        return JSON.toJSONString(map);
    }
}

