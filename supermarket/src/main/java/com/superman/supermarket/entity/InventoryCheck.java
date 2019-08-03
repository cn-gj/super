package com.superman.supermarket.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("inventory_check")
public class InventoryCheck implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer storeId;

    private Integer checkScopeId;

    @TableField("createDate")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    @JSONField(format = "yyyy-MM-dd")
    private Date checkDate;

    private Integer checkStateId;

    private Integer empId;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public Integer getCheckScopeId() {
        return checkScopeId;
    }

    public void setCheckScopeId(Integer checkScopeId) {
        this.checkScopeId = checkScopeId;
    }



    public Integer getCheckStateId() {
        return checkStateId;
    }

    public void setCheckStateId(Integer checkStateId) {
        this.checkStateId = checkStateId;
    }



    @Override
    public String toString() {
        return "InventoryCheck{" +
        "id=" + id +
        ", checkScopeId=" + checkScopeId +
        ", createDate=" + createDate +
        ", checkDate=" + checkDate +
        ", checkStateId=" + checkStateId +
        "}";
    }
}
