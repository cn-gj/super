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
@TableName("oper_info")
public class OperInfo implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDate operDate;

    private String remark;

    private Integer operState;

    private Integer empId;

    private Integer operModelId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getOperDate() {
        return operDate;
    }

    public void setOperDate(LocalDate operDate) {
        this.operDate = operDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOperState() {
        return operState;
    }

    public void setOperState(Integer operState) {
        this.operState = operState;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getOperModelId() {
        return operModelId;
    }

    public void setOperModelId(Integer operModelId) {
        this.operModelId = operModelId;
    }

    @Override
    public String toString() {
        return "OperInfo{" +
        "id=" + id +
        ", operDate=" + operDate +
        ", remark=" + remark +
        ", operState=" + operState +
        ", empId=" + empId +
        ", operModelId=" + operModelId +
        "}";
    }
}
