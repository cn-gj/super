package com.superman.supermarket.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("whole_order")
public class WholeOrder implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer customerId;

    private Integer shopId;

    @JSONField(format = "yyyy-MM-dd")
    private Date placeOrderDate;

    @JSONField(format = "yyyy-MM-dd")
    private Date readyDate;

    private Integer empId;

    //private Integer odetailId;

    private Integer singleState;

    private Integer takeState;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
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

   /* public Integer getOdetailId() {
        return odetailId;
    }

    public void setOdetailId(Integer odetailId) {
        this.odetailId = odetailId;
    }*/

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
        return "WholeOrder{" +
        "id=" + id +
        ", customerId=" + customerId +
        ", shopId=" + shopId +
        ", placeOrderDate=" + placeOrderDate +
        ", readyDate=" + readyDate +
        ", empId=" + empId +
        //", odetailId=" + odetailId +
        ", singleState=" + singleState +
        ", takeState=" + takeState +
        "}";
    }
}
