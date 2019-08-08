package com.superman.supermarket.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.superman.supermarket.entity.Goods;
import com.superman.supermarket.entity.InventoryDetail;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.entity.Store;

public class InventoryDetailVo extends InventoryDetail {
    /**
     *  仓库
     */
    @TableField(exist = false)
    private Store store;

    //商品
    @TableField(exist = false)
    private Goods goods;

    //门店
    @TableField(exist = false)
    private Shop shop;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    private String goodsName;   // 商品名称
    private String goodsCode;   // 商品条码
    private String condition;   // 查询条件(商品名称/商品条码)

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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    // 商品成本价
    private Double costPrice;

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }
}
