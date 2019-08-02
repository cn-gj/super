package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Goods;
import com.superman.supermarket.entity.GoodsType;
import com.superman.supermarket.service.GoodsTypeService;
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
@RequestMapping("/goodsType")
public class GoodsTypeController {

    @Resource
    private GoodsTypeService goodsTypeService;

    /**
     *  根据条件查询商品类型信息
     * @param goodsType
     * @return
     */
    @PostMapping("/findByCondition")
    @ResponseBody
    public String findByCondition(GoodsType goodsType){
        return JSON.toJSONString(goodsTypeService.findByCondition(goodsType));
    }

    /**
     *  根据类别名称查询类型对象
     * @param typeName
     * @return
     */
    @PostMapping("/findByName")
    @ResponseBody
    public String findByName(String typeName){
        GoodsType goodsType = new GoodsType();
        goodsType.setGoodsTypeName(typeName);
        Map<String,Object> map = new HashMap<>();
        if (goodsTypeService.findByCondition(goodsType) != null && goodsTypeService.findByCondition(goodsType).size() > 0){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return  JSON.toJSONString(map);
    }


    /**
     *  添加商品类型
     * @param goodsType
     * @return
     */
    @PostMapping("/addGoodsType")
    @ResponseBody
    public String addGoodsType(GoodsType goodsType){
        Map<String,Object> map = new HashMap<>();
        if (goodsTypeService.addGoodsType(goodsType) > 0){
            map.put("result",true);
        }else{
            map.put("result",false);
        }
        return  JSON.toJSONString(map);
    }


    /**
     *  修改商品类型
     * @param goodsType
     * @return
     */
    @PostMapping("/modifyGoodsType")
    @ResponseBody
    public String modifyGoodsType(GoodsType goodsType){
        Map<String,Object> map = new HashMap<>();
        if (goodsTypeService.modifyById(goodsType) > 0){
            map.put("result",true);
        }else{
            map.put("result",false);
        }
        return  JSON.toJSONString(map);
    }

    /**
     *  删除商品类型
     * @param ids
     * @return
     */
    @PostMapping("/deleteGoodsTypeById")
    @ResponseBody
    public String deleteGoodsTypeById(int [] ids){
        Map<String,Object> map = new HashMap<>();
        if (goodsTypeService.deleteByIds(ids) > 0){
            map.put("result",true);
        }else{
            map.put("result",false);
        }
        return  JSON.toJSONString(map);
    }

    /**
     * 查看商品类型下有没商品信息
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/selGoodsByType")
    public String selectGoodsByGoodsType(int [] ids){
        Map<String,Object> map = new HashMap<>();
        if (goodsTypeService.selectGoodsByGoodsType(ids)){
            map.put("result",true);
        }else{
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }


}

