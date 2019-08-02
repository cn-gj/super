package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.entity.vo.ShopVo;
import com.superman.supermarket.service.ShopService;
import com.superman.supermarket.service.ShopTypeService;
import com.superman.supermarket.utils.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

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
    public String toShop(ShopVo shopVO){
        System.out.println(shopVO);
        //存放返回到页面的数据
        Map<String,Object> map = new HashMap<>();
        //调用方法查询门店列表信息
        List<ShopVo> shopList= shopService.getShopInfoByCondition(shopVO);
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
     * 根据门店id、员工状态查询员工信息
     * @param id
     * @param empStatus
     * @return
     */
    @ResponseBody
    @PostMapping("/selEmpByShopId")
    public String selectEmpByShopId(Integer id, Integer empStatus){
        return JSON.toJSONString(shopService.selectEmpByShopId(id,empStatus));
    }

    /**
     * 修改门店信息
     * @return
     */
    @PostMapping("/upShop")
    @ResponseBody
    public String UpdateShopInfo(Shop shop, MultipartFile fileName, HttpServletRequest request){
        //存放返回到页面的数据
        Map<String,Object> map = new HashMap<>();
        try {
            if (fileName != null){
                //获取根路径
                File path = new File(System.getProperty("user.dir"));
                System.out.println(path);
                //文件上传路径、项目路径
                File upLoadPath = new File(path.getAbsolutePath(),"static/upload/images");
                System.out.println(upLoadPath);
                //如果路径不存在则创建
                if(!upLoadPath.exists()) upLoadPath.mkdirs();
                //生成uuid
                String uuid = UUID.randomUUID().toString().replace("-","");
                //获取旧文件名
                String oldFileName = fileName.getOriginalFilename();
                //获取文件名后缀
                String suffixName = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();
                //拼接新文件名
                String newFileName ="\\"+ uuid + suffixName;
                try {
                    //获取文件名、并设置进shop对象
                    shop.setShopLogo(newFileName);
                    System.out.println(upLoadPath+newFileName);
                    //添加文件到本地磁盘
                    fileName.transferTo(new File(upLoadPath+newFileName));
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                    /*return "请重新上传图片";*/
                }
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
        } catch (Exception e) {
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

