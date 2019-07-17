package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.entity.ShopType;
import com.superman.supermarket.entity.vo.ShopVO;
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
@RequestMapping("/shop")
public class ShopController {
    @Resource
    private ShopTypeService shopTypeService;
    @Resource
    private ShopService shopService;


    /**
     * 多条件查询门店信息
     * @param shopVO
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @PostMapping("/toShopList")
    private String toShop(ShopVO shopVO, Integer page, Integer rows){
        //存放返回到页面的数据
        Map<String,Object> map = new HashMap<>();
        //调用方法查询门店类别信息
        List<ShopType> shopTypes = shopTypeService.findShopTypeList();
        //调用方法查询所有门店名称
        List<Shop> shops = shopService.findAllShopName();
        //获取分页信息对象
//        Page<ShopVO> pageInfo = new Page<ShopVO>(page,rows);
        //调用方法查询门店列表信息
        List<ShopVO> shopVOIPage= shopService.getShopInfoByCondition(shopVO);
        //把门店类别信息存放到map集合
        map.put("shopTypeList",shopTypes);
        //把所有门店名称存放到map集合
        map.put("shopList",shops);
        //获取总记录数、并保存到map
       // map.put("total", shopVOIPage.getTotal());
        //获取门店列表信息、并保存到map
       // map.put("rows", shopVOIPage.getRecords());
        //返回json数据
        return JSON.toJSONString(map);
    }



}

