package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Store;
import com.superman.supermarket.entity.vo.StoreVo;
import com.superman.supermarket.service.StoreService;
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
@RequestMapping("/store")
public class StoreController {
    @Resource
    private StoreService storeService;

    /**
     *  根据店铺id查询仓库
     * @param shopId
     * @return
     */
    @PostMapping("/findByShopId")
    @ResponseBody
    public String findByShopId(Integer shopId){
        return JSON.toJSONString(storeService.findByShopId(shopId));
    }


    /**
     *查询仓库信息、或多条件查询
     * @param storeVo
     * @return
     */
    @PostMapping("/selStoreAndShop")
    @ResponseBody
    public String selStoreAndShop(StoreVo storeVo){
        return JSON.toJSONString(storeService.selStoreAndShop(storeVo));
    }


    /**
     * 新增仓库信息
     * @param store
     * @return
     */
    @PostMapping("/insertStore")
    @ResponseBody
    public String insertStore(Store store){
        Map<String,Object> map = new HashMap<>();
        Integer count = storeService.insertStore(store);
        if (count > 0 ){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除仓库信息
     * @param storeId
     * @return
     */
    @PostMapping("/delStore")
    @ResponseBody
    public String deleteStore(Integer storeId){
        Map<String,Object> map = new HashMap<>();
        try {
            boolean flag = storeService.deleteStore(storeId);
            map.put("state",flag);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除仓库信息
     * @param store
     * @return
     */
    @PostMapping("/updateStore")
    @ResponseBody
    public String updateStore(Store store){
        Map<String,Object> map = new HashMap<>();
        try {
           int rowCount = storeService.updateStoreNameById(store);
           if (rowCount > 0){
               map.put("state",true);
           }else {
               map.put("state",false);
           }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }
}

