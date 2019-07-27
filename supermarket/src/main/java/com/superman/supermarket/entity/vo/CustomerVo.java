package com.superman.supermarket.entity.vo;

import com.superman.supermarket.entity.Customer;

public class CustomerVo extends Customer {

    /**
     *  条件
     */
    private String condition;

    /*
    * 所属店铺ID
    * */
    private String shopId;
    /**
     *  所属店铺名称
     */
    private String shopName;

    /*
    * 所属员工
    * */
    private String empName;

    /**
     *  员工id
     */
    private Integer empId;

    /*
    * 员工联系电话
    * */
    private String empPhone;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }
}
