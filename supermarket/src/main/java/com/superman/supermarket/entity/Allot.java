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
@TableName("allot")
public class Allot implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer inShopId;

    private Integer outShopId;

    private Integer allotType;

    private Integer allotState;

    private Integer singleState;

    private Double singleMoney;

    private LocalDate singleDate;

    private Integer allotDetailId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInShopId() {
        return inShopId;
    }

    public void setInShopId(Integer inShopId) {
        this.inShopId = inShopId;
    }

    public Integer getOutShopId() {
        return outShopId;
    }

    public void setOutShopId(Integer outShopId) {
        this.outShopId = outShopId;
    }

    public Integer getAllotType() {
        return allotType;
    }

    public void setAllotType(Integer allotType) {
        this.allotType = allotType;
    }

    public Integer getAllotState() {
        return allotState;
    }

    public void setAllotState(Integer allotState) {
        this.allotState = allotState;
    }

    public Integer getSingleState() {
        return singleState;
    }

    public void setSingleState(Integer singleState) {
        this.singleState = singleState;
    }

    public Double getSingleMoney() {
        return singleMoney;
    }

    public void setSingleMoney(Double singleMoney) {
        this.singleMoney = singleMoney;
    }

    public LocalDate getSingleDate() {
        return singleDate;
    }

    public void setSingleDate(LocalDate singleDate) {
        this.singleDate = singleDate;
    }

    public Integer getAllotDetailId() {
        return allotDetailId;
    }

    public void setAllotDetailId(Integer allotDetailId) {
        this.allotDetailId = allotDetailId;
    }

    @Override
    public String toString() {
        return "Allot{" +
        "id=" + id +
        ", inShopId=" + inShopId +
        ", outShopId=" + outShopId +
        ", allotType=" + allotType +
        ", allotState=" + allotState +
        ", singleState=" + singleState +
        ", singleMoney=" + singleMoney +
        ", singleDate=" + singleDate +
        ", allotDetailId=" + allotDetailId +
        "}";
    }
}
