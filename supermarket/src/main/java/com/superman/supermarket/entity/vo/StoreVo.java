package com.superman.supermarket.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.superman.supermarket.entity.InventoryDetail;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.entity.Store;

import java.util.List;

/**
 *  仓库Vo
 */
public class StoreVo extends Store {

    @TableField(exist = false)
    private Shop shop; //门店

    @TableField(exist = false)
    private String condition;//查询条件

    @TableField(exist = false)
    private Integer storeId;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    /**
     *  库存明细
     */
    private List<InventoryDetail> details;

    public List<InventoryDetail> getDetails() {
        return details;
    }

    public void setDetails(List<InventoryDetail> details) {
        this.details = details;
    }
}
