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
@TableName("goods")
public class Goods implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String goodsName;

    private byte[] goodsCode;

    private Double price;

    private Double memberPrice;

    private Double wholePrice;

    private Double costPrice;

    private Integer specTmpId;

    private String unitId;

    private Integer goodsTypeId;

    private Integer providerId;

    private String goodsAddress;

    private LocalDate yieldDate;

    private String expirationDay;

    private LocalDate expirationDate;


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

    public byte[] getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(byte[] goodsCode) {
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

    public Integer getSpecTmpId() {
        return specTmpId;
    }

    public void setSpecTmpId(Integer specTmpId) {
        this.specTmpId = specTmpId;
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

    public LocalDate getYieldDate() {
        return yieldDate;
    }

    public void setYieldDate(LocalDate yieldDate) {
        this.yieldDate = yieldDate;
    }

    public String getExpirationDay() {
        return expirationDay;
    }

    public void setExpirationDay(String expirationDay) {
        this.expirationDay = expirationDay;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
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
        ", specTmpId=" + specTmpId +
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
