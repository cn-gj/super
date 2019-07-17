package com.superman.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@TableName("provider")
public class Provider implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String proName;

    private String proLinkman;

    private String proPhone;

    private String proEmail;

    private String bank;

    private String bankNum;

    private String proAddress;

    private Double shouldMoney;

    private Integer proStatus;

    private Double readyMoney;

    @TableField("createDate")
    private LocalDate createDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProLinkman() {
        return proLinkman;
    }

    public void setProLinkman(String proLinkman) {
        this.proLinkman = proLinkman;
    }

    public String getProPhone() {
        return proPhone;
    }

    public void setProPhone(String proPhone) {
        this.proPhone = proPhone;
    }

    public String getProEmail() {
        return proEmail;
    }

    public void setProEmail(String proEmail) {
        this.proEmail = proEmail;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public String getProAddress() {
        return proAddress;
    }

    public void setProAddress(String proAddress) {
        this.proAddress = proAddress;
    }

    public Double getShouldMoney() {
        return shouldMoney;
    }

    public void setShouldMoney(Double shouldMoney) {
        this.shouldMoney = shouldMoney;
    }

    public Integer getProStatus() {
        return proStatus;
    }

    public void setProStatus(Integer proStatus) {
        this.proStatus = proStatus;
    }

    public Double getReadyMoney() {
        return readyMoney;
    }

    public void setReadyMoney(Double readyMoney) {
        this.readyMoney = readyMoney;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Provider{" +
        "id=" + id +
        ", proName=" + proName +
        ", proLinkman=" + proLinkman +
        ", proPhone=" + proPhone +
        ", proEmail=" + proEmail +
        ", bank=" + bank +
        ", bankNum=" + bankNum +
        ", proAddress=" + proAddress +
        ", shouldMoney=" + shouldMoney +
        ", proStatus=" + proStatus +
        ", readyMoney=" + readyMoney +
        ", createDate=" + createDate +
        "}";
    }
}
