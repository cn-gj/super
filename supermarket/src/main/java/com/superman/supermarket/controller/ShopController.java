package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.entity.vo.ShopVO;
import com.superman.supermarket.service.ShopService;
import com.superman.supermarket.service.ShopTypeService;
import com.superman.supermarket.utils.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
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
    private ShopTypeService shopTypeService;//门店类型service
    @Resource
    private ShopService shopService; //门店service



    /**
     * 多条件查询门店信息
     * @param shopVO
     * @return
     */
    @PostMapping("/shopList")
    @ResponseBody
    public String toShop(ShopVO shopVO){
        System.out.println(shopVO);
        //存放返回到页面的数据
        Map<String,Object> map = new HashMap<>();
        //调用方法查询门店列表信息
        List<ShopVO> shopList= shopService.getShopInfoByCondition(shopVO);

        System.out.println(shopList);
        map.put("shopList",shopList);
        //返回json数据
        return JSON.toJSONString(map);
    }

    /**
     * 显示所有门店名称
     * @return
     */
    @ResponseBody
    @PostMapping("/shopNameList")
    public String showShopNameList(){
        return shopService.findAllShopName();
    }

    /**
     * 查询所有门店类型信息
     * @return
     */
    @ResponseBody
    @PostMapping("/shopTypeList")
    public String showShopTypeList(){
        return shopTypeService.findShopTypeList();
    }

    /**
     * 根据账户或门店名查询门店信息
     * @return
     */
    @ResponseBody
    @PostMapping("/accountAndName")
    public String findShopByAccount(String account,String shopName){
        //存放返回到页面的数据
        Map<String,Object> map = new HashMap<>();
        map.put("shopAccount",shopService.findShopByAccount(account));
        map.put("shopName",shopService.getShopInfoByShopName(shopName));
        return  JSON.toJSONString(map);
    }


    /**
     * 添加门店信息
     * @param shop
     * @return
     */
    @PostMapping("/addShop")
    @ResponseBody
    public  String addShop(Shop shop){
        //存放返回到页面的数据
        Map<String,Object> map = new HashMap<>();
        //调用方法添加
        Integer count = shopService.addShop(shop);
        if (count > 0){
            //成功
            map.put("state",true);
        }else {
            //失败
            map.put("state",false);
        }
        //返回json数据
        return  JSON.toJSONString(map);
    }


    /**
     * 修改门店信息
     * @return
     */
    @PostMapping("/upShop")
    @ResponseBody
    public String UpdateShopInfo(Shop shop,MultipartFile fileName){
        //存放返回到页面的数据
        Map<String,Object> map = new HashMap<>();
        try {
            if (fileName != null){
                //获取文件名、并设置进shop对象
                shop.setShopLogo("\\upload\\images\\"+fileName.getOriginalFilename());
                //添加文件到images目录
                fileName.transferTo(new File("\\upload\\images\\"+fileName.getOriginalFilename()));
            }
            //调用方法添加
            Integer count = shopService.updateShopInfo(shop);
            if (count > 0){
                //成功
                map.put("state",true);
            }else {
                //失败
                map.put("state",false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回json数据
        return  JSON.toJSONString(map);
    }


    /**
     * 删除门店信息
     * @param id
     * @return
     */
    @PostMapping("/delShop")
    @ResponseBody
    public String deleteShop(Integer id){
        //存放返回到页面的数据
        Map<String,Object> map = new HashMap<>();
        //调用方法添加
        Integer count = shopService.delShopInfo(id);
        if (count > 0){
            //成功
            map.put("state",true);
        }else {
            //失败
            map.put("state",false);
        }
        //返回json数据
        return  JSON.toJSONString(map);
    }



    /**
     *  导出门店信息Execl
     * @return
     */
    @GetMapping("/exportShop")
    public String exportEmp(HttpServletResponse response){
        String fileName = "门店信息列表_"+ DateUtil.date2Str(new Date()) +".xls";
        //中文名称进行转码
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(fileName.getBytes(),"ISO-8859-1"));
            // 调用业务层导出excel表格
            try {
                shopService.exportShop(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  null;
    }
}

