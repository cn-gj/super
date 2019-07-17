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
@TableName("store")
public class Store implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String storeName;

    private Integer shopId;

    private Integer invDetailId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getInvDetailId() {
        return invDetailId;
    }

    public void setInvDetailId(Integer invDetailId) {
        this.invDetailId = invDetailId;
    }

    @Override
    public String toString() {
        return "Store{" +
        "id=" + id +
        ", storeName=" + storeName +
        ", shopId=" + shopId +
        ", invDetailId=" + invDetailId +
        "}";
    }
}
