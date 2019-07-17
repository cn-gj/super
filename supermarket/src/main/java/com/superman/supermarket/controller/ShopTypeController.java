package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.ShopType;
import com.superman.supermarket.service.ShopService;
import com.superman.supermarket.service.ShopTypeService;
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
@RequestMapping("/shopType")
public class ShopTypeController {

    @Resource
    private ShopTypeService shopTypeService;

    /**
     * 显示门店类别信息
     * @return
     */
    @PostMapping("/showShopTypeList")
    @ResponseBody
    public String showShopTypeList(){
        return shopTypeService.findShopTypeList();
    }


    /**
     * 添加门店类别信息
     * @return
     */
    @PostMapping("/addShopType")
    @ResponseBody
    public String addShopType(ShopType shopType){
        //存放返回页面的数据
        Map<String,Object> map = new HashMap<>();
        Integer count = shopTypeService.addShopType(shopType);
        if (count > 0){
            //成功
            map.put("state",true);
        }else {
            //失败
            map.put("state",false);
        }
        //返回map数据
        return JSON.toJSONString(map);
    }



    /**
     * 修改门店类别信息
     * @return
     */
    @PostMapping("/upShopType")
    @ResponseBody
    public String updateShopType(ShopType shopType){
        //存放返回页面的数据
        Map<String,Object> map = new HashMap<>();
        Integer count = shopTypeService.upShopType(shopType);
        if (count > 0){
            //成功
            map.put("state",true);
        }else {
            //失败
            map.put("state",false);
        }
        //返回map数据
        return JSON.toJSONString(map);
    }


    /**
     * 删除门店类别信息
     * @return
     */
    @PostMapping("/delShopType")
    @ResponseBody
    public String deleteShopType(Integer id){
        //存放返回页面的数据
        Map<String,Object> map = new HashMap<>();
        System.out.println(id);
        Integer count = shopTypeService.delShopType(id);
        if (count > 0){
            //成功
            map.put("state",true);
        }else {
            //失败
            map.put("state",false);
        }
        //返回map数据
        return JSON.toJSONString(map);
    }
}

