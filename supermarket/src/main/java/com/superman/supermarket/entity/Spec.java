package com.superman.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@TableName("spec")
public class Spec implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String specName;

    private Integer specTmpId;

    /**
     * 规格详情、一个规格对应多个详情
     */
    @TableField(exist = false)
    private List<SpecDetail> sdList;

    public List<SpecDetail> getSdList() {
        return sdList;
    }

    public void setSdList(List<SpecDetail> sdList) {
        this.sdList = sdList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Integer getSpecTmpId() {
        return specTmpId;
    }

    public void setSpecTmpId(Integer specTmpId) {
        this.specTmpId = specTmpId;
    }

    @Override
    public String toString() {
        return "Spec{" +
        "id=" + id +
        ", specName=" + specName +
        ", specTmpId=" + specTmpId +
        "}";
    }
}
