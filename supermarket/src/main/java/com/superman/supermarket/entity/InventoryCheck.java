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
@TableName("inventory_check")
public class InventoryCheck implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer shopId;

    private Integer checkScopeId;

    @TableField("createDate")
    private LocalDate createDate;

    private LocalDate checkDate;

    private Integer checkStateId;

    private Integer checkDetailId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getCheckScopeId() {
        return checkScopeId;
    }

    public void setCheckScopeId(Integer checkScopeId) {
        this.checkScopeId = checkScopeId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDate checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getCheckStateId() {
        return checkStateId;
    }

    public void setCheckStateId(Integer checkStateId) {
        this.checkStateId = checkStateId;
    }

    public Integer getCheckDetailId() {
        return checkDetailId;
    }

    public void setCheckDetailId(Integer checkDetailId) {
        this.checkDetailId = checkDetailId;
    }

    @Override
    public String toString() {
        return "InventoryCheck{" +
        "id=" + id +
        ", shopId=" + shopId +
        ", checkScopeId=" + checkScopeId +
        ", createDate=" + createDate +
        ", checkDate=" + checkDate +
        ", checkStateId=" + checkStateId +
        ", checkDetailId=" + checkDetailId +
        "}";
    }
}
