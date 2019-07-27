package com.superman.supermarket.entity.vo;

import com.superman.supermarket.entity.Goods;
import com.superman.supermarket.entity.TicketDetail;

/**
 *  收银单详情vo
 */
public class TicketDetailVo extends TicketDetail {
    /**
     *  商品
     */
    private Goods goods;

    /**
     *  规格详情名称
     */
    private String specDetailName;

    /**
     *  规格名称
     */
    private String specName;

    /**
     *  模版名称
     */
    private String specTmpName;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getSpecDetailName() {
        return specDetailName;
    }

    public void setSpecDetailName(String specDetailName) {
        this.specDetailName = specDetailName;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecTmpName() {
        return specTmpName;
    }

    public void setSpecTmpName(String specTmpName) {
        this.specTmpName = specTmpName;
    }
}
