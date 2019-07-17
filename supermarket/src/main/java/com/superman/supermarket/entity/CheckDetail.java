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
@TableName("check_detail")
public class CheckDetail implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer ismodify;

    private Integer goodsId;

    private Integer invDetailId;

    private Integer checkCount;

    private Integer bepCount;

    private Double costPrice;

    private Double originalPrice;

    private Double checkMoney;

    private Double bepMoney;

    private String causeInfo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsmodify() {
        return ismodify;
    }

    public void setIsmodify(Integer ismodify) {
        this.ismodify = ismodify;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getInvDetailId() {
        return invDetailId;
    }

    public void setInvDetailId(Integer invDetailId) {
        this.invDetailId = invDetailId;
    }

    public Integer getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(Integer checkCount) {
        this.checkCount = checkCount;
    }

    public Integer getBepCount() {
        return bepCount;
    }

    public void setBepCount(Integer bepCount) {
        this.bepCount = bepCount;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getCheckMoney() {
        return checkMoney;
    }

    public void setCheckMoney(Double checkMoney) {
        this.checkMoney = checkMoney;
    }

    public Double getBepMoney() {
        return bepMoney;
    }

    public void setBepMoney(Double bepMoney) {
        this.bepMoney = bepMoney;
    }

    public String getCauseInfo() {
        return causeInfo;
    }

    public void setCauseInfo(String causeInfo) {
        this.causeInfo = causeInfo;
    }

    @Override
    public String toString() {
        return "CheckDetail{" +
        "id=" + id +
        ", ismodify=" + ismodify +
        ", goodsId=" + goodsId +
        ", invDetailId=" + invDetailId +
        ", checkCount=" + checkCount +
        ", bepCount=" + bepCount +
        ", costPrice=" + costPrice +
        ", originalPrice=" + originalPrice +
        ", checkMoney=" + checkMoney +
        ", bepMoney=" + bepMoney +
        ", causeInfo=" + causeInfo +
        "}";
    }
}
