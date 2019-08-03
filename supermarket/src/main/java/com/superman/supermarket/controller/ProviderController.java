package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Provider;
import com.superman.supermarket.entity.vo.ProviderVo;
import com.superman.supermarket.service.ProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/provider")
public class ProviderController {

    @Resource
    private ProviderService providerService;

    /**
     * 查询供应商列表、多条件
     * @return
     */
    @ResponseBody
    @PostMapping("/proList")
    public String findProviderList(ProviderVo providerVo){
        Map<String,Object> map = new HashMap<>();
        List<ProviderVo> providerVoList = providerService.findSelectProvider(providerVo);
        map.put("providerList",providerVoList);
        return JSON.toJSONString(map);
    }

    /**
     * 添加供应商、同时往中间表添加
     * @param provider
     * @param shopId
     * @return
     */
    @ResponseBody
    @PostMapping("/addPro")
    public String addProviderAndProShop(Provider provider, Integer shopId){
        Map<String,Object> map = new HashMap<>();
        try {
            Integer count = providerService.findInsertProvider(provider,shopId);
            map.put("state",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改供应商
     * @param provider
     * @return
     */
    @ResponseBody
    @PostMapping("/upPro")
    public String updateProvider(Provider provider){
        Map<String,Object> map = new HashMap<>();
        Integer count = providerService.updateProvider(provider);
        if (count > 0){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 批量删除供应商
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/delPro")
    public String deleteProvider(int [] ids){
        Map<String,Object> map = new HashMap<>();
        try {
            Integer count = providerService.deleteProvider(ids);
            map.put("state",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 批量修改供应商状态
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/upProStatus")
    public String updateProviderStatus(int [] ids,Integer proStatus){
        Map<String,Object> map = new HashMap<>();
        try {
            Integer count = providerService.updateProStatus(ids,proStatus);
            map.put("state",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 根据供应商名称查询该门店下的供应商信息
     * @param proName
     * @param shopId
     * @return
     */
    @ResponseBody
    @PostMapping("/selProByProNameAndShopId")
    public String selProviderByProNameAndShopId(String proName,Integer shopId){
        Map<String,Object>map = new HashMap<>();
        ProviderVo providerVo= providerService.selProviderByProNameAndShopId(proName,shopId);
        if (providerVo != null){
            map.put("state",true);//存在
            map.put("proId",providerVo.getId());
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }
}

