package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.vo.InventoryCheckVo;
import com.superman.supermarket.service.InventoryCheckService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/inventoryCheck")
public class InventoryCheckController {

    @Resource
    private InventoryCheckService inventoryCheckService;

    /**
     *  添加库存盘点收银单
     * @param inventoryCheckVo
     * @return
     */
    @PostMapping("/addInvCheck")
    @ResponseBody
    public String addInvCheck(InventoryCheckVo inventoryCheckVo){
        Map<String,Object> map = new HashMap<>();
        map.put("state",inventoryCheckService.addInvCheck(inventoryCheckVo));
        return JSON.toJSONString(map);
    }

    /**
     * 初始化和根据条件查询库存盘点清单
     * @param inventoryCheckVo
     * @return
     */
    @PostMapping("/findByCondition")
    @ResponseBody
    public String findByCondition(InventoryCheckVo inventoryCheckVo){
        return JSON.toJSONString(inventoryCheckService.findByCondition(inventoryCheckVo));
    }

    /**
     * 根据的盘点单id查询盘点单和该盘点单下的明细
     * @param id
     * @return
     */
    @PostMapping("/findById")
    @ResponseBody
    public String findById(int id){
        return JSON.toJSONString(inventoryCheckService.findById(id));
    }


    /**
     * 修改库存盘点单的状态
     * @param inventoryCheckVo
     * @return
     */
    @PostMapping("/updateInventoryCheck")
    @ResponseBody
    public String updateInventoryCheck(InventoryCheckVo inventoryCheckVo){
        Map<String,Object> map = new HashMap<>();
        if (inventoryCheckService.updateInventoryCheck(inventoryCheckVo) > 0){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }


    /**
     * 修改库存盘点单的状态
     * @param jsonStr
     * @return
     */
    @PostMapping("/addCheck")
    @ResponseBody
    public String addCheck(InventoryCheckVo inventoryCheckVo,String jsonStr){
        Map<String,Object> map = new HashMap<>();
        try {
            inventoryCheckService.addCheck(inventoryCheckVo,jsonStr);
            map.put("state",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }
}

