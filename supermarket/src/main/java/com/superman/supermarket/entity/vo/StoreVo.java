package com.superman.supermarket.entity.vo;

import com.superman.supermarket.entity.InventoryDetail;
import com.superman.supermarket.entity.Store;

import java.util.List;

/**
 *  仓库Vo
 */
public class StoreVo extends Store {
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
