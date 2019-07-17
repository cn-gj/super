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
@TableName("ticket")
public class Ticket implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer cashierDeskId;

    private LocalDate placeOrderDate;

    private Integer memberId;

    private Double originalCountMoney;

    private Double practicalMoney;

    private Double discountMoney;

    private Integer sellCount;

    private Integer singleScore;

    private Integer empId;

    private Integer ticketId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCashierDeskId() {
        return cashierDeskId;
    }

    public void setCashierDeskId(Integer cashierDeskId) {
        this.cashierDeskId = cashierDeskId;
    }

    public LocalDate getPlaceOrderDate() {
        return placeOrderDate;
    }

    public void setPlaceOrderDate(LocalDate placeOrderDate) {
        this.placeOrderDate = placeOrderDate;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Double getOriginalCountMoney() {
        return originalCountMoney;
    }

    public void setOriginalCountMoney(Double originalCountMoney) {
        this.originalCountMoney = originalCountMoney;
    }

    public Double getPracticalMoney() {
        return practicalMoney;
    }

    public void setPracticalMoney(Double practicalMoney) {
        this.practicalMoney = practicalMoney;
    }

    public Double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Double discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public Integer getSingleScore() {
        return singleScore;
    }

    public void setSingleScore(Integer singleScore) {
        this.singleScore = singleScore;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
        "id=" + id +
        ", cashierDeskId=" + cashierDeskId +
        ", placeOrderDate=" + placeOrderDate +
        ", memberId=" + memberId +
        ", originalCountMoney=" + originalCountMoney +
        ", practicalMoney=" + practicalMoney +
        ", discountMoney=" + discountMoney +
        ", sellCount=" + sellCount +
        ", singleScore=" + singleScore +
        ", empId=" + empId +
        ", ticketId=" + ticketId +
        "}";
    }
}
