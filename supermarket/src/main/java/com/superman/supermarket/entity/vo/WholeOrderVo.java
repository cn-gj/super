package com.superman.supermarket.entity.vo;

import com.superman.supermarket.entity.WholeOrder;

import java.util.List;

/**
 *  批发订单Vo类
 */
public class WholeOrderVo extends WholeOrder {

    /**
     *  订单明细
     */
    private List<OrderDetailVo> details;

    /**
     *  订单金额
     */
    private Double whoMoney;

    /**
     *  查询条件
     */
    private String condition;

    /**
     *  店铺名称
     */
    private String shopName;

    /**
     *  客户名称
     */
    private String CustomerName;

    /**
     *  员工名称
     */
    private String empName;

    /**
     *  员工联系电话
     */
    private String empPhone;

    /**
     *  店铺地址
     */
    private String shopAddress;

    /**
     *  时间查询,开始时间
     */
    private String startDate;

    /**
     *  时间查询,结束时间
     */
    private String endDate;

    private String customerLinkMan;     // 客户联系人
    private String customerAddress;     // 客户地址

    public String getCustomerLinkMan() {
        return customerLinkMan;
    }

    public void setCustomerLinkMan(String customerLinkMan) {
        this.customerLinkMan = customerLinkMan;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Double getWhoMoney() {
        return whoMoney;
    }

    public void setWhoMoney(Double whoMoney) {
        this.whoMoney = whoMoney;
    }

    public List<OrderDetailVo> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetailVo> details) {
        this.details = details;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }
}
