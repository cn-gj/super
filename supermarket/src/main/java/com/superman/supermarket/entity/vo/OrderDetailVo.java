package com.superman.supermarket.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.superman.supermarket.entity.Goods;
import com.superman.supermarket.entity.OrderDetail;

import java.util.List;

public class OrderDetailVo extends OrderDetail {
    @TableField(exist = false)
    private GoodsVo goodsVo;//商品、一个带订单明细对应一个商品

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }
}
