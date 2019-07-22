package com.superman.supermarket.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.superman.supermarket.entity.Level;
import com.superman.supermarket.entity.Member;
import com.superman.supermarket.entity.Shop;

public class MemberVo extends Member {
    //查询条件
    @TableField(exist = false)
    private String condition;
    //所属门店
    @TableField(exist = false)
    private Shop shop;
    //会员类型
    @TableField(exist = false)
    private Level level;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
