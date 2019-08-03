package com.superman.supermarket.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.superman.supermarket.entity.CashierDesk;
import com.superman.supermarket.entity.Shop;

/**
 *  收银台VO
 */
public class CashierDeskVO extends CashierDesk {

    /**
     *  模糊查询条件
     * 根据收银台号,收银台名称,店名
     */
    private String condition;
    /**
     *  商店
     */
    private Shop shop;

    @TableField(exist = false)
    private Integer shopId;

    @Override
    public Integer getShopId() {
        return shopId;
    }

    @Override
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }


}
