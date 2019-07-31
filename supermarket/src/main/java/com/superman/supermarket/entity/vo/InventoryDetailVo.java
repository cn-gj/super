package com.superman.supermarket.entity.vo;

import com.superman.supermarket.entity.InventoryDetail;
import com.superman.supermarket.entity.Store;

public class InventoryDetailVo extends InventoryDetail {
    /**
     *  仓库
     */
    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
