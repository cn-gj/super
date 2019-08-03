package com.superman.supermarket.entity.vo;

import com.superman.supermarket.entity.InventoryCheck;

import java.util.List;

/**
 *  库存盘点vo
 */
public class InventoryCheckVo extends InventoryCheck {
    private String storeName;  // 归属仓库名称
    private String scopeName;  // 盘点范围名称
    private String empName;    // 操作员名称
    private String stateName;  // 状态名称
    private String condition;  // 查询条件
    private String startDate;   // 开始时间
    private String endDate;     // 结束时间
    // 盘点清单下的盘点明细
    private List<CheckDetailVo> detailVos;

    public List<CheckDetailVo> getDetailVos() {
        return detailVos;
    }

    public void setDetailVos(List<CheckDetailVo> detailVos) {
        this.detailVos = detailVos;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
