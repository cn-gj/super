package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.CashierDesk;
import com.superman.supermarket.entity.vo.CashierDeskVO;
import com.superman.supermarket.service.CashierDeskService;
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
@RequestMapping("/cashierDesk")
public class CashierDeskController {
    @Resource
    private CashierDeskService cashierDeskService;

    /**
     *  条件查询
     * @param cashierDeskVO
     * @return
     */
    @PostMapping("/findByCondition")
    @ResponseBody
    public String findByCondtion(CashierDeskVO cashierDeskVO){
        return JSON.toJSONString(cashierDeskService.findByConidtion(cashierDeskVO));
    }


    /**
     *  根据id查询收银台信息
     * @param id
     * @return
     */
    @PostMapping("/findById")
    @ResponseBody
    public String findById(Integer id){
        return JSON.toJSONString(cashierDeskService.findById(id));
    }

    /**
     *  根据名称查询收银台信息
     * @param cashName
     * @return
     */
    @PostMapping("/findByName")
    @ResponseBody
    public String findByName(String cashName){
        return JSON.toJSONString(cashierDeskService.findByName(cashName));
    }

    /**
     *  添加收银台
     * @param cashierDesk
     * @return
     */
    @PostMapping("/addCash")
    @ResponseBody
    public String addCash(CashierDesk cashierDesk){
        int rowCount = cashierDeskService.addCash(cashierDesk);
        Map<String,Object> map = new HashMap<>();
        if (rowCount > 0){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }


    /**
     *  修改收银台
     * @param cashierDeskVO
     * @return
     */
    @PostMapping("/modifyCash")
    @ResponseBody
    public String modifyCash(CashierDeskVO cashierDeskVO){
        int rowCount = cashierDeskService.modifyCash(cashierDeskVO);
        Map<String,Object> map = new HashMap<>();
        if (rowCount > 0){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }

}

