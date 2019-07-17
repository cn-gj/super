package com.superman.supermarket.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("shop")
public class Shop implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String shopName;

    private String shopAccount;

    private String shopPwd;

    private String shopLinkman;

    private String shopPhone;

    private String shopLogo;

    private String shopHours;

    private String shopAdvice;

    private String shopAddress;

    private Integer shopTypeId;

    @TableField("createDate")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAccount() {
        return shopAccount;
    }

    public void setShopAccount(String shopAccount) {
        this.shopAccount = shopAccount;
    }

    public String getShopPwd() {
        return shopPwd;
    }

    public void setShopPwd(String shopPwd) {
        this.shopPwd = shopPwd;
    }

    public String getShopLinkman() {
        return shopLinkman;
    }

    public void setShopLinkman(String shopLinkman) {
        this.shopLinkman = shopLinkman;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopHours() {
        return shopHours;
    }

    public void setShopHours(String shopHours) {
        this.shopHours = shopHours;
    }

    public String getShopAdvice() {
        return shopAdvice;
    }

    public void setShopAdvice(String shopAdvice) {
        this.shopAdvice = shopAdvice;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Integer getShopTypeId() {
        return shopTypeId;
    }

    public void setShopTypeId(Integer shopTypeId) {
        this.shopTypeId = shopTypeId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Shop{" +
        "id=" + id +
        ", shopName=" + shopName +
        ", shopAccount=" + shopAccount +
        ", shopPwd=" + shopPwd +
        ", shopLinkman=" + shopLinkman +
        ", shopPhone=" + shopPhone +
        ", shopLogo=" + shopLogo +
        ", shopHours=" + shopHours +
        ", shopAdvice=" + shopAdvice +
        ", shopAddress=" + shopAddress +
        ", shopTypeId=" + shopTypeId +
        ", createDate=" + createDate +
        "}";
    }
}
