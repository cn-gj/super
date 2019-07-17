package com.superman.supermarket.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.entity.ShopType;

import java.util.Date;

public class ShopVO extends Shop {

    @TableField(exist=false)
    private Date terminationDate;

    @TableField(exist=false)
    private ShopType shopType;

    public ShopType getShopType() {
        return shopType;
    }

    public void setShopType(ShopType shopType) {
        this.shopType = shopType;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }
}
