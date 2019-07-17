package com.superman.supermarket.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.entity.ShopType;

import java.util.Date;

public class ShopVO extends Shop {

    @TableField(exist=false)
    private String beginDate;  //起始日期

    @TableField(exist=false)
    private String endDate;  //终止日期

    @TableField(exist=false)
    private ShopType shopType;



    public ShopType getShopType() {
        return shopType;
    }

    public void setShopType(ShopType shopType) {
        this.shopType = shopType;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
