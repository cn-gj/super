package com.superman.supermarket.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.List;

/**
 *  报表类
 */
public class ShopSummary {
    private String shopName;
    @TableField(exist = false)
    List<BTicket> bTicketList;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<BTicket> getbTicketList() {
        return bTicketList;
    }

    public void setbTicketList(List<BTicket> bTicketList) {
        this.bTicketList = bTicketList;
    }
}

//// 门店类
//class BShop{
//    private Integer id;
//
//    private String shopName;
//    @TableField(exist = false)
//    List<BTicket> bTicketList;
//
//    public String getShopName() {
//        return shopName;
//    }
//
//    public void setShopName(String shopName) {
//        this.shopName = shopName;
//    }
//    public List<BTicket> getbTicketList() {
//        return bTicketList;
//    }
//
//    public void setbTicketList(List<BTicket> bTicketList) {
//        this.bTicketList = bTicketList;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//}

// 小票
class BTicket{
    private Integer id;

    private Double practicalMoney;
    @TableField(exist = false)
    List<BTicketDetail> bTicketDetailList;

    public Double getPracticalMoney() {
        return practicalMoney;
    }

    public void setPracticalMoney(Double practicalMoney) {
        this.practicalMoney = practicalMoney;
    }

    public List<BTicketDetail> getbTicketDetailList() {
        return bTicketDetailList;
    }

    public void setbTicketDetailList(List<BTicketDetail> bTicketDetailList) {
        this.bTicketDetailList = bTicketDetailList;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

// 小票明细
class BTicketDetail{
    private Integer id;

    private Integer goodsCount;
    @TableField(exist = false)
    private BGoods bGoods;
    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BGoods getbGoods() {
        return bGoods;
    }

    public void setbGoods(BGoods bGoods) {
        this.bGoods = bGoods;
    }
}

class BGoods{
    private Integer id;

    private Double costPrice;
    private Double price;

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
