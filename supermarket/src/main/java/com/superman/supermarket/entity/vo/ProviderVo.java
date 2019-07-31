package com.superman.supermarket.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.superman.supermarket.entity.Provider;
import com.superman.supermarket.entity.Shop;

import java.util.List;

public class ProviderVo extends Provider {

    @TableField(exist = false)
    private List<Shop> shopList;

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

}