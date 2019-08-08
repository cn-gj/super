package com.superman.supermarket.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.dao.CheckDetailMapper;
import com.superman.supermarket.dao.GoodsMapper;
import com.superman.supermarket.dao.InventoryCheckMapper;
import com.superman.supermarket.dao.InventoryDetailMapper;
import com.superman.supermarket.entity.CheckDetail;
import com.superman.supermarket.entity.Goods;
import com.superman.supermarket.entity.InventoryCheck;
import com.superman.supermarket.entity.InventoryDetail;
import com.superman.supermarket.entity.vo.CheckDetailVo;
import com.superman.supermarket.entity.vo.InventoryCheckVo;
import com.superman.supermarket.entity.vo.InventoryDetailVo;
import com.superman.supermarket.service.InventoryCheckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
public class InventoryCheckServiceImpl extends ServiceImpl<InventoryCheckMapper, InventoryCheck> implements InventoryCheckService {

    @Resource
    private InventoryCheckMapper inventoryCheckMapper;
    @Resource
    private InventoryDetailMapper inventoryDetailMapper;
    @Resource
    private CheckDetailMapper checkDetailMapper;
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public Boolean addInvCheck(InventoryCheckVo inventoryCheckVo) {
        int rowCount = 0;
        // 判断库存盘点单的盘点类型
        if (inventoryCheckVo.getCheckScopeId() == 1){
            // 人工盘点
            // 添加库存盘点单
            // 设置操作人id从session中获取-----------
            rowCount = inventoryCheckMapper.addInvCheck(inventoryCheckVo);
            if (rowCount > 0){
                return true;
            }else {
                return false;
            }
        }else if (inventoryCheckVo.getCheckScopeId() == 3){
            // 全场盘点
            // 添加库存盘点单
            // 设置操作人id从session中获取---------
           rowCount = inventoryCheckMapper.addInvCheck(inventoryCheckVo);
            // 根据仓库id获取该仓库的库存明细
            // 构建查询条件
            InventoryDetailVo inventoryDetailVo =  new InventoryDetailVo();
            inventoryDetailVo.setStoreId(inventoryCheckVo.getStoreId());
            List<InventoryDetailVo> details =  inventoryDetailMapper.findByStoreId(inventoryDetailVo);
            // 根据库存明细列表和库存盘点单id添加盘点明细
            // 判断该仓库下是否有库存明细
            if (details != null && details.size() >0){
                CheckDetail checkDetail = null;
                Goods goods = null;
                int totalCount = details.size()+1;  // 添加成功受影响行数
                // 循环添加盘点库存明细单
                for (InventoryDetail invdetail:details) {
                    checkDetail = new CheckDetail();
                    checkDetail.setGoodsId(invdetail.getGoodsId());  //商品id
                    checkDetail.setInvDetailId(invdetail.getId());   // 对应库存中的那一条数据
                    checkDetail.setCheckCount(0);                    // 盘点数量默认0
                    // 盈亏数量:如果库存数量是正数盈亏数量就是库存的负数反之一样
                    if (invdetail.getGoodsCount() > 0){
                        // 正数
                        checkDetail.setBepCount(invdetail.getGoodsCount() - invdetail.getGoodsCount() * 2);
                    }else {
                        // 负数
                        checkDetail.setBepCount(invdetail.getGoodsCount() + invdetail.getGoodsCount() * 2);
                    }
                    // 根据商品id查询商品信息
                     goods = goodsMapper.selectById(invdetail.getGoodsId());
                    // 商品成本价
                    checkDetail.setCostPrice(goods.getCostPrice());
                    // 原库存金额==>商品金额 * 商品库存数量 = 库存金额
                    checkDetail.setOriginalPrice(goods.getPrice() * invdetail.getGoodsCount());
                    // 盘点金额默认0
                    checkDetail.setCheckMoney(0.00);
                    // 盈亏金额==>盈亏数量 * 商品成本价
                    checkDetail.setBepMoney(checkDetail.getBepCount() * goods.getPrice());
                    // 该盘点明细属于那个盘点单
                    checkDetail.setInvCheckedId(inventoryCheckVo.getId());
                    // 是否修改库存,默认不修改
                    checkDetail.setIsmodify(0);
                    // 持久化保存
                    rowCount += checkDetailMapper.insert(checkDetail);
                }
                if (rowCount == totalCount){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return  true;   // 默认添加成功
    }


    @Override
    public List<InventoryCheckVo> findByCondition(InventoryCheckVo inventoryCheckVo) {
        return inventoryCheckMapper.findByCondition(inventoryCheckVo);
    }

    @Override
    public InventoryCheckVo findById(int invId) {
        // 根据id查询盘点单
        InventoryCheckVo inventoryCheckVo = inventoryCheckMapper.findById(invId);
        // 根据盘点单id查询盘点明细
        inventoryCheckVo.setDetailVos(checkDetailMapper.findByInvId(invId));
        return inventoryCheckVo;
    }

    @Override
    public int updateInventoryCheck(InventoryCheckVo inventoryCheckVo) {
        return inventoryCheckMapper.updateById(inventoryCheckVo);
    }



    //------------------------------------------------

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean addCheck(InventoryCheckVo inventoryCheckVo,String jsonStr) {
        // 将字符串转换成jsonObject对象
        JSONArray array = JSON.parseArray(jsonStr);
        CheckDetailVo checkDetailVo2 = null;
        List<CheckDetailVo> checkDetailVos = new ArrayList<>();
        if (array != null && array.size() > 0) {
            // 循环将array中的元素转换为实体类
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = (JSONObject) array.get(i);
                checkDetailVo2 = JSONObject.toJavaObject(object,CheckDetailVo.class);
                checkDetailVo2.setInvDetailId(object.getInteger("id"));
                checkDetailVos.add(checkDetailVo2);
            }
        }
        // 将json字符串转换成实体类
        //InventoryCheckVo inventoryCheckVo = JSONObject.toJavaObject(jsonObject,InventoryCheckVo.class);
        inventoryCheckVo.setDetailVos(checkDetailVos);
        // 成功总受影响行数
        int totalCount = 1;
        // 添加库存盘点单,返回盘点的id
       int rowCount =  inventoryCheckMapper.addInvCheck(inventoryCheckVo);
        // 判断盘点单中的detailVos是否有数据
        if (inventoryCheckVo.getDetailVos() != null && inventoryCheckVo.getDetailVos().size() > 0){
            totalCount += inventoryCheckVo.getDetailVos().size();
            // 根据盘点单id循环添加盘点明细
            for (CheckDetailVo checkDetailVo:inventoryCheckVo.getDetailVos()) {
                // 设置该盘点明细属于那个盘点单
                checkDetailVo.setInvCheckedId(inventoryCheckVo.getId());
                    rowCount += checkDetailMapper.insert(checkDetailVo);
            }
        }
        // 判断是否添加成功
        if (rowCount == totalCount){
            return true;
        }else {
            return false;
        }
    }
}
