package com.superman.supermarket.entity.vo;

import com.superman.supermarket.entity.OrderDetail;
import com.superman.supermarket.entity.WholeOrder;

import java.util.List;

/**
 *  批发订单Vo类
 */
public class WholeOrderVo extends WholeOrder {

    /**
     *  订单明细
     */
    private List<OrderDetail> details;

    /**
     *  订单金额
     */
    private Double whoMoney;

    /**
     *  查询条件
     */
    private String condiion;

    public String getCondiion() {
        return condiion;
    }

    public void setCondiion(String condiion) {
        this.condiion = condiion;
    }

    public Double getWhoMoney() {
        return whoMoney;
    }

    public void setWhoMoney(Double whoMoney) {
        this.whoMoney = whoMoney;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }
}
