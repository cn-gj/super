package com.superman.supermarket.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@TableName("order")
public class Order implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String shopId;

    @JSONField(format = "yyyy-MM-dd")
    private Date placeOrderDate;

    @JSONField(format = "yyyy-MM-dd")
    private Date readyDate;

    private Integer empId;

    private Integer singleState;

    private Integer proId;

    private Integer takeState;

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Date getPlaceOrderDate() {
        return placeOrderDate;
    }

    public void setPlaceOrderDate(Date placeOrderDate) {
        this.placeOrderDate = placeOrderDate;
    }

    public Date getReadyDate() {
        return readyDate;
    }

    public void setReadyDate(Date readyDate) {
        this.readyDate = readyDate;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getSingleState() {
        return singleState;
    }

    public void setSingleState(Integer singleState) {
        this.singleState = singleState;
    }

    public Integer getTakeState() {
        return takeState;
    }

    public void setTakeState(Integer takeState) {
        this.takeState = takeState;
    }

    @Override
    public String toString() {
        return "Order{" +
        "id=" + id +
        ", shopId=" + shopId +
        ", placeOrderDate=" + placeOrderDate +
        ", readyDate=" + readyDate +
        ", empId=" + empId +
        ", singleState=" + singleState +
        ", takeState=" + takeState +
        "}";
    }
}
