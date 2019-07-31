package com.superman.supermarket.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.text.Format;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@TableName("cashier_desk")
public class CashierDesk implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String cashierName;

    private Integer shopId;

    private Integer cashierState;

    @TableField("createDate")
    @JSONField(format="yyyy-MM-dd")
    private Date createDate;

    @JSONField(format="yyyy-MM-dd")
    @TableField("modifyDate")
    private Date modifyDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getCashierState() {
        return cashierState;
    }

    public void setCashierState(Integer cashierState) {
        this.cashierState = cashierState;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "CashierDesk{" +
        "id=" + id +
        ", cashierName=" + cashierName +
        ", shopId=" + shopId +
        ", cashierState=" + cashierState +
        ", createDate=" + createDate +
        ", modifyDate=" + modifyDate +
        "}";
    }
}
