package com.superman.supermarket.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("goods")
public class Goods implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String goodsName;

    private String goodsCode;

    private Double price;

    private Double memberPrice;

    private Double wholePrice;

    private Double costPrice;

    private Integer specDetailId;

    private String unitId;

    private Integer goodsTypeId;

    private Integer providerId;

    private String goodsAddress;
    @JSONField(format = "yyyy/MM/dd")
    private Date yieldDate;

    private String expirationDay;
    @JSONField(format = "yyyy/MM/dd")
    private Date expirationDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(Double memberPrice) {
        this.memberPrice = memberPrice;
    }

    public Double getWholePrice() {
        return wholePrice;
    }

    public void setWholePrice(Double wholePrice) {
        this.wholePrice = wholePrice;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getSpecDetailId() {
        return specDetailId;
    }

    public void setSpecDetailId(Integer specDetailId) {
        this.specDetailId = specDetailId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getGoodsAddress() {
        return goodsAddress;
    }

    public void setGoodsAddress(String goodsAddress) {
        this.goodsAddress = goodsAddress;
    }

    public Date getYieldDate() {
        return yieldDate;
    }

    public void setYieldDate(Date yieldDate) {
        this.yieldDate = yieldDate;
    }

    public String getExpirationDay() {
        return expirationDay;
    }

    public void setExpirationDay(String expirationDay) {
        this.expirationDay = expirationDay;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Goods{" +
        "id=" + id +
        ", goodsName=" + goodsName +
        ", goodsCode=" + goodsCode +
        ", price=" + price +
        ", memberPrice=" + memberPrice +
        ", wholePrice=" + wholePrice +
        ", costPrice=" + costPrice +
        ", specTmpId=" + specDetailId +
        ", unitId=" + unitId +
        ", goodsTypeId=" + goodsTypeId +
        ", providerId=" + providerId +
        ", goodsAddress=" + goodsAddress +
        ", yieldDate=" + yieldDate +
        ", expirationDay=" + expirationDay +
        ", expirationDate=" + expirationDate +
        "}";
    }
}
