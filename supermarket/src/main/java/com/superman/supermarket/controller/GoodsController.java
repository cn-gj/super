package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Goods;
import com.superman.supermarket.entity.vo.GoodsVo;
import com.superman.supermarket.service.GoodsService;
import com.superman.supermarket.utils.DateUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
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
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private GoodsService goodsService;

    /**
     *  根据条件查询商品信息请求
     * @param goodsVo
     * @return
     */
    @PostMapping("/findByCondition")
    @ResponseBody
    public String findByCondition(GoodsVo goodsVo){
        return JSON.toJSONString(goodsService.findByCondition(goodsVo));
    }


    /**
     *  根据商品条码查询商品信息
     * @param goodsCode
     * @return
     */
    @PostMapping("/findByGoodsCode")
    @ResponseBody
        public String findByGoodsCode(String goodsCode){
        Map<String,Object> map = new HashMap<>();
        Goods goods = goodsService.findByGoodsCode(goodsCode);
        if ( goods!= null){
            map.put("result",true);
            map.put("goods",goods);
        }else {
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }

    @ResponseBody
    @PostMapping("/selGoodsAndIndByGoodsCode")
    public String selGoodsAndInventoryDetailByGoodsCode(String goodsCode){
       return JSON.toJSONString(goodsService.selGoodsAndInventoryDetailByGoodsCode(goodsCode));
    }
    /**
     *  根据商品名称查询商品信息
     * @param goodsName
     * @return
     */
    @PostMapping("/findByGoodsName")
    @ResponseBody
    public String findByGoodsName(String goodsName){
        Map<String,Object> map = new HashMap<>();
        if (goodsService.findByGoodsName(goodsName) != null){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     *  添加商品
     * @param goods
     * @return
     */
    @PostMapping("/addGoods")
    @ResponseBody
    public String addGoods(Goods goods){
        Map<String,Object> map = new HashMap<>();
        if (goodsService.addGoods(goods) > 0){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return  JSON.toJSONString(map);
    }

    /**
     *  批量删除商品请求
     * @param ids
     * @return
     */
    @PostMapping("/batchDeleteGoods")
    @ResponseBody
    public String batchDeleteGoods(int[] ids){
        int len = ids.length;
        Map<String,Object> map = new HashMap<>();
        if (goodsService.batchDelete(ids) == len){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     *  批量修改商品类型请求
     * @param ids
     * @param goodsTypeId
     * @return
     */
    @PostMapping("/batchModifyGoods")
    @ResponseBody
    public  String batchModifyGoods(int[] ids,int goodsTypeId){
        int len = ids.length;
        Map<String,Object> map = new HashMap<>();
        if (goodsService.batchModify(ids,goodsTypeId) == len){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     *  根据id查询商品信息
     * @param id
     * @return
     */
    @PostMapping("findById")
    @ResponseBody
    public String findById(Integer id,Integer providerId){
        return JSON.toJSONString(goodsService.findById(id,providerId));
    }


    /**
     *  导出商品信息Execl
     * @return
     */
    @GetMapping("/exportGoods")
    public String exportGoods(HttpServletResponse response){
        String fileName = "商品信息列表_"+ DateUtil.date2Str(new Date()) +".xls";
        //中文名称进行转码
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(fileName.getBytes(),"ISO-8859-1"));
            // 调用业务层导出excel表格
            try {
                goodsService.exportGoods(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     *  修改商品
     * @param goods
     * @return
     */
    @PostMapping("/updateGoods")
    @ResponseBody
    public String updateGoods(Goods goods){
        Map<String,Object> map = new HashMap<>();
        if (goodsService.updateGoods(goods) > 0){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return  JSON.toJSONString(map);
    }

    /**
     * 根据供应商id查询商品信息
     * @param providerId
     * @return
     */
    @ResponseBody
    @PostMapping("/selGoodsByProId")
    public String selGoodsByProviderId(Integer providerId){
        return JSON.toJSONString(goodsService.selGoodsByProviderId(providerId));
    }
}

