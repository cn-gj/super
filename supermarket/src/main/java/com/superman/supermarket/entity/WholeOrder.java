package com.superman.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

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

    private LocalDate placeOrderDate;

    private LocalDate readyDate;

    private Integer empId;

    private Integer odetailId;

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

    public LocalDate getPlaceOrderDate() {
        return placeOrderDate;
    }

    public void setPlaceOrderDate(LocalDate placeOrderDate) {
        this.placeOrderDate = placeOrderDate;
    }

    public LocalDate getReadyDate() {
        return readyDate;
    }

    public void setReadyDate(LocalDate readyDate) {
        this.readyDate = readyDate;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getOdetailId() {
        return odetailId;
    }

    public void setOdetailId(Integer odetailId) {
        this.odetailId = odetailId;
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
        return "WholeOrder{" +
        "id=" + id +
        ", customerId=" + customerId +
        ", shopId=" + shopId +
        ", placeOrderDate=" + placeOrderDate +
        ", readyDate=" + readyDate +
        ", empId=" + empId +
        ", odetailId=" + odetailId +
        ", singleState=" + singleState +
        ", takeState=" + takeState +
        "}";
    }
}
