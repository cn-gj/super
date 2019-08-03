package com.superman.supermarket.entity.vo;

import com.superman.supermarket.entity.CheckDetail;

/**
 *  库存盘点明细Vo
 */
public class CheckDetailVo extends CheckDetail {
    private String goodsName;   // 商品名称
    private String goodsCode;   // 商品条码
    private Integer goodsCount; // 商品库存

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }
}
