package com.superman.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("spec_detail")
public class SpecDetail implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String specDetailName;

    private Integer specId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecDetailName() {
        return specDetailName;
    }

    public void setSpecDetailName(String specDetailName) {
        this.specDetailName = specDetailName;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    @Override
    public String toString() {
        return "SpecDetail{" +
        "id=" + id +
        ", specDetailName=" + specDetailName +
        ", specId=" + specId +
        "}";
    }
}
