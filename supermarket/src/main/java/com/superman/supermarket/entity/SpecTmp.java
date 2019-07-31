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
@TableName("spec_tmp")
public class SpecTmp implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String specTmpName;

    /**
     * 规格 、一个规格模板对应多个规格
     */
    @TableField(exist = false)
    private List<Spec> specList;
    /**
     * 用于接收规格数
     */
    @TableField(exist = false)
    private Integer sCount;//查询条件

    @TableField(exist = false)
    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getsCount() {
        return sCount;
    }

    public void setsCount(Integer sCount) {
        this.sCount = sCount;
    }

    public List<Spec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecTmpName() {
        return specTmpName;
    }

    public void setSpecTmpName(String specTmpName) {
        this.specTmpName = specTmpName;
    }

    @Override
    public String toString() {
        return "SpecTmp{" +
        "id=" + id +
        ", specDetailName=" + specTmpName +
        "}";
    }
}
