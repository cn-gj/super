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
@TableName("spec_tmp")
public class SpecTmp implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String specDetailName;


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

    @Override
    public String toString() {
        return "SpecTmp{" +
        "id=" + id +
        ", specDetailName=" + specDetailName +
        "}";
    }
}
