package com.superman.supermarket.service.impl;

import com.superman.supermarket.dao.GoodsMapper;
import com.superman.supermarket.entity.CheckDetail;
import com.superman.supermarket.dao.CheckDetailMapper;
import com.superman.supermarket.entity.Goods;
import com.superman.supermarket.entity.InventoryDetail;
import com.superman.supermarket.entity.vo.CheckDetailVo;
import com.superman.supermarket.entity.vo.InventoryDetailVo;
import com.superman.supermarket.service.CheckDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Service
public class CheckDetailServiceImpl extends ServiceImpl<CheckDetailMapper, CheckDetail> implements CheckDetailService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public CheckDetailVo creatByInvDetailId(InventoryDetailVo inventoryDetailVo,int inCheckId) {
        CheckDetailVo checkDetailVo = new CheckDetailVo();
        Goods goods = null;

        checkDetailVo.setGoodsId(inventoryDetailVo.getGoodsId());  //商品id
        checkDetailVo.setInvDetailId(inventoryDetailVo.getId());   // 对应库存中的那一条数据
        checkDetailVo.setCheckCount(0);                    // 盘点数量默认0
        // 盈亏数量:如果库存数量是正数盈亏数量就是库存的负数反之一样
        if (inventoryDetailVo.getGoodsCount() > 0){
            // 正数
            checkDetailVo.setBepCount(inventoryDetailVo.getGoodsCount() - inventoryDetailVo.getGoodsCount() * 2);
        }else {
            // 负数
            checkDetailVo.setBepCount(inventoryDetailVo.getGoodsCount() + inventoryDetailVo.getGoodsCount() * 2);
        }
        // 根据商品id查询商品信息
        goods = goodsMapper.selectById(inventoryDetailVo.getGoodsId());
        // 商品成本价
        checkDetailVo.setCostPrice(goods.getCostPrice());
        // 原库存金额==>商品金额 * 商品库存数量 = 库存金额
        checkDetailVo.setOriginalPrice(goods.getPrice() * inventoryDetailVo.getGoodsCount());
        // 盘点金额默认0
        checkDetailVo.setCheckMoney(0.00);
        // 盈亏金额==>盈亏数量 * 商品成本价
        checkDetailVo.setBepMoney(checkDetailVo.getBepCount() * goods.getPrice());
        // 该盘点明细属于那个盘点单
        checkDetailVo.setInvCheckedId(inCheckId);
        // 是否修改库存,默认不修改
        checkDetailVo.setIsmodify(0);
        // 商品名称
        checkDetailVo.setGoodsName(inventoryDetailVo.getGoodsName());
        // 商品条码
        checkDetailVo.setGoodsCode(inventoryDetailVo.getGoodsCode());
        return checkDetailVo;
    }
}
