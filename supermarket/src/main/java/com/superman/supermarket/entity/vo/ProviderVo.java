package com.superman.supermarket.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.superman.supermarket.entity.Provider;
import com.superman.supermarket.entity.Shop;

import java.util.List;

public class ProviderVo extends Provider {

    @TableField(exist = false)
    private Integer shopId;

    @TableField(exist = false)
    private List<Shop> shopList;

    @TableField(exist = false)
    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

}
