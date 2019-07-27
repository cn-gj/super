package com.superman.supermarket.entity.vo;

import com.superman.supermarket.entity.Ticket;

import java.util.Date;
import java.util.List;

/**
 *  收银单vo
 */
public class TicketVo extends Ticket {
    /**
     *  收银单下的详情列表
     */
    private List<TicketDetailVo> details;

    /**
     * 归属店铺
     */
    private String shopName;

    /**
     * 收银台名称
     */
    private String cashName;

    /**
     *  会员名称
     */
    private String memberName;

    /**
     *  收银员名称
     */
    private String empName;

    /**
     *  时间查询条件,结束日期
     */
    private String endDate;

    /**
     *  开始时间
     */
    private String startDate;

    /**
     *  查询条件
     */
    private String condition;

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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCashName() {
        return cashName;
    }

    public void setCashName(String cashName) {
        this.cashName = cashName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public List<TicketDetailVo> getDetails() {
        return details;
    }

    public void setDetails(List<TicketDetailVo> details) {
        this.details = details;
    }
}
