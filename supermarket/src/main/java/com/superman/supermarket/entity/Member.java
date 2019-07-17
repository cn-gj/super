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
@TableName("member")
public class Member implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String memberName;

    private String memberPhone;

    private LocalDate memberBirthday;

    private String memberSex;

    private Integer shopId;

    private Integer levelId;

    private Double totalMoney;

    private Integer totalSorce;

    private Double residueMoney;

    private Integer residueSorce;

    @TableField("createDate")
    private LocalDate createDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public LocalDate getMemberBirthday() {
        return memberBirthday;
    }

    public void setMemberBirthday(LocalDate memberBirthday) {
        this.memberBirthday = memberBirthday;
    }

    public String getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(String memberSex) {
        this.memberSex = memberSex;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getTotalSorce() {
        return totalSorce;
    }

    public void setTotalSorce(Integer totalSorce) {
        this.totalSorce = totalSorce;
    }

    public Double getResidueMoney() {
        return residueMoney;
    }

    public void setResidueMoney(Double residueMoney) {
        this.residueMoney = residueMoney;
    }

    public Integer getResidueSorce() {
        return residueSorce;
    }

    public void setResidueSorce(Integer residueSorce) {
        this.residueSorce = residueSorce;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Member{" +
        "id=" + id +
        ", memberName=" + memberName +
        ", memberPhone=" + memberPhone +
        ", memberBirthday=" + memberBirthday +
        ", memberSex=" + memberSex +
        ", shopId=" + shopId +
        ", levelId=" + levelId +
        ", totalMoney=" + totalMoney +
        ", totalSorce=" + totalSorce +
        ", residueMoney=" + residueMoney +
        ", residueSorce=" + residueSorce +
        ", createDate=" + createDate +
        "}";
    }
}
