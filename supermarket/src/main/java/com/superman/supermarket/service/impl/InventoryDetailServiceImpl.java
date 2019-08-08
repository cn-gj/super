package com.superman.supermarket.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.dao.InventoryDetailMapper;
import com.superman.supermarket.entity.InventoryDetail;
import com.superman.supermarket.entity.vo.InventoryDetailVo;
import com.superman.supermarket.service.InventoryDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Service
@Transactional
public class InventoryDetailServiceImpl extends ServiceImpl<InventoryDetailMapper, InventoryDetail> implements InventoryDetailService {

    @Resource
    private InventoryDetailMapper inventoryDetailMapper;

    /**
     * 根据采购收获商品数量修改库存明细
     * @param goodsStr
     * @return
     */
    @Override
    public Boolean updateInventoryByOrderDetail(Integer storeId,String goodsStr) {
        List<Map<String, Object>> orderDetailMapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> orderDetailMap = new HashMap<>();
        System.out.println("仓库id：++++++++++++++++===="+storeId);
        System.out.println(goodsStr);
        //把json字符串转化为json数组
        JSONArray array = JSON.parseArray(goodsStr);
        //调用方法查询仓库中存在的商品id
        List<InventoryDetail> goodsIdList = inventoryDetailMapper.selInventoryByStoreId(storeId);
        // 遍历array,获取arry中Json对象商品id已存在于goodsArray中的json对象，和不存在于其中的json对象
        List<Map<String, Object>> exist = new ArrayList<>();
        List<Map<String, Object>> noExist = new ArrayList<>();
        // 已存在的
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = (JSONObject) array.get(i);
            for (int j = 0; j < goodsIdList.size(); j++) {
                if (object.getInteger("goodsId") == goodsIdList.get(j).getGoodsId()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("goodsId", object.getInteger("goodsId"));
                    map.put("goodsCount", object.getInteger("goodsCount"));
                    map.put("storeId", storeId);
                    exist.add(map);
                }
            }
        }

        // 不存在的 2 4
        for (int i = 0; i < array.size(); i++) {
            int count = 0;
            JSONObject object = (JSONObject) array.get(i);
            for (InventoryDetail item : goodsIdList) {
                if (item.getGoodsId() != object.getInteger("goodsId")) {
                    count++;
                }
            }
            if (count == goodsIdList.size()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("goodsId", object.getInteger("goodsId"));
                map.put("goodsCount", object.getInteger("goodsCount"));
                map.put("storeId", storeId);
                noExist.add(map);
            }
        }
        for (Map<String,Object> map : exist){
            System.out.println("存在="+map.get("goodsId")+"-"+map.get("goodsCount")+"-"+map.get("storeId"));
        }

        for (Map<String,Object> map : noExist){
            System.out.println("不存在="+map.get("goodsId")+"-"+map.get("goodsCount")+"-"+map.get("storeId"));
        }
        int count = 0;
        int num = 0;


        if (exist.size() != 0){
            //商品存在、调用修改的方法、修改仓库中的商品库存
           count = inventoryDetailMapper.updateInventoryByOrderDetail(exist);
            System.out.println("存在");
        }
        if (noExist.size() != 0){
            //商品不存在、调用新增的方法、像仓库中添加商品库存
           num = inventoryDetailMapper.insertInventoryDetailByProviderTake(noExist);
            System.out.println("不存在");
        }
        if (count + num == array.size()){
            return  true;
        }
        return false;
    }

    /**
     * 根据采购退货单的商品信息修改库存明细
     * 根据商品id和库存id批量修改库存明细
     * @param storeId
     * @param goodsStr
     * @return
     */
    @Override
    public Boolean updateInventoryByOrderDetailReturn(Integer storeId, String goodsStr) {
        List<Map<String, Object>> orderDetailMapList = new ArrayList<>();
        Map<String, Object> orderDetailMap = new HashMap<>();

        //把json字符串转化为json数组
        JSONArray array = JSON.parseArray(goodsStr);
        //调用方法查询仓库中存在的商品id
        List<InventoryDetail> goodsIdList = inventoryDetailMapper.selInventoryByStoreId(storeId);
        // 遍历array,获取arry中Json对象商品id已存在于goodsArray中的json对象，和不存在于其中的json对象
        List<Map<String,Object>> exist = new ArrayList<>();
        List<Map<String,Object>> noExist = new ArrayList<>();
        // 已存在的
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = (JSONObject) array.get(i);
            for (int j = 0; j < goodsIdList.size(); j++) {
                //判断传入的goodsId与库存中的商品id是否一致
                if (object.getInteger("goodsId") == goodsIdList.get(j).getGoodsId()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("goodsId", object.getInteger("goodsId"));
                    map.put("goodsCount", object.getInteger("goodsCount"));
                    map.put("storeId", storeId);
                    exist.add(map);
                }
            }
        }

        // 不存在的 2 4
        for (int i = 0; i < array.size(); i++) {
            int count = 0;
            JSONObject object = (JSONObject) array.get(i);
            for (InventoryDetail item : goodsIdList) {
                if (item.getGoodsId() != object.getInteger("goodsId")) {
                    count++;
                }
            }
            if (count == goodsIdList.size()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("goodsId", object.getInteger("goodsId"));
                noExist.add(map);
            }
        }

        // 仓库中不存在要进行退货的商品
        if (noExist.size()>0){
            return false;
        }
        // 判断库存中的商品数量是否满足退货需求
        boolean flag = true;
        for (Map<String,Object> map : exist){
            for (InventoryDetail item : goodsIdList){
                if (map.get("goodsId") == item.getGoodsId()){
                    if ((int)map.get("goodsCount")>item.getGoodsCount()){
                        flag = false;
                    }
                }
            }
        }
        //  库存不足
        if (flag == false){
            return false;
        }
        // 同步库存
        int count = inventoryDetailMapper.updateInventoryByOrderDetailReturn(exist);
        if (count == exist.size()){
            return true;
        }
        return false;
    }

    /**
     * 根据仓库id查询仓库下的商品信息
     * @param storeId
     * @return
     */
    @Override
    public List<InventoryDetail> selGoodsByStoreId(Integer storeId) {
        return inventoryDetailMapper.selGoodsByStoreId(storeId);
    }

    @Override
    public List<InventoryDetailVo> findByStoreId(InventoryDetailVo inventoryDetailVo) {
        return inventoryDetailMapper.findByStoreId(inventoryDetailVo);
    }

    //----------------------------------
    @Override
    public List<InventoryDetailVo> selectByStoreId(int storeId) {
        return inventoryDetailMapper.selectByStoreId(storeId);
    }

    /**
     *根据仓库id查询仓库、库存明细、商品、门店信息
     * @param storeId
     * @return
     */
    @Override
    public List<InventoryDetailVo> selInventoryDetailByStoreId(Integer storeId) {
        return inventoryDetailMapper.selInventoryDetailByStoreId(storeId);
    }

    /**
     * 查询仓库中商品总数量
     * @param storeId
     * @return
     */
    @Override
    public Integer selInventoryDetailCount(Integer storeId) {
        return inventoryDetailMapper.selInventoryDetailCount(storeId);
    }
}
