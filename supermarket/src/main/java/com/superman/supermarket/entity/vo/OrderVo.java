package com.superman.supermarket.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.superman.supermarket.entity.Employee;
import com.superman.supermarket.entity.Order;
import com.superman.supermarket.entity.Provider;
import com.superman.supermarket.entity.Shop;

import java.util.List;

public class OrderVo extends Order {
    @TableField(exist=false)
    private String beginDate;  //起始日期

    @TableField(exist=false)
    private String endDate;  //终止日期

    @TableField(exist = false)
    private Employee employee; //员工

    @TableField
    private String condition;//多条件

    @TableField(exist = false)
    private Shop shop; //门店

    @TableField(exist = false)
    private Provider provider;//供应商

    @TableField(exist = false)
    private List<OrderDetailVo> orderDetailVoList; //订单明细、一个订单对应多个明细

    public List<OrderDetailVo> getOrderDetailVoList() {
        return orderDetailVoList;
    }

    public void setOrderDetailVoList(List<OrderDetailVo> orderDetailVoList) {
        this.orderDetailVoList = orderDetailVoList;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
