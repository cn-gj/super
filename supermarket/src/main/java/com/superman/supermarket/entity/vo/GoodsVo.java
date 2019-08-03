package com.superman.supermarket.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.superman.supermarket.entity.*;

/**
 * 商品Vo
 */
public class GoodsVo extends Goods {
    // 商品条码或者商品名称,查询条件
    private String condition;

    // 商品库存
   private Integer goodsCount;

    // 商品类型名称
    private String goodsTypeName;

    // 供应商
    private String proName;


    // 规格名称
    private String spceTmpName;
    // 规格模版名称
    private String specName;



    @TableField(exist = false)
    private InventoryDetail inventoryDetail;//一个商品对应一个库存明细

    public InventoryDetail getInventoryDetail() {
        return inventoryDetail;
    }

    public void setInventoryDetail(InventoryDetail inventoryDetail) {
        this.inventoryDetail = inventoryDetail;
    }

    /**
     * 排序名称
     */
    private String sortName;
    /**
     * 排序类型
     */
    private String sortType;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getSpceTmpName() {
        return spceTmpName;
    }

    public void setSpceTmpName(String spceTmpName) {
        this.spceTmpName = spceTmpName;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecDetailName() {
        return specDetailName;
    }

    public void setSpecDetailName(String specDetailName) {
        this.specDetailName = specDetailName;
    }

    // 商品规格详情
    private String specDetailName;
}
