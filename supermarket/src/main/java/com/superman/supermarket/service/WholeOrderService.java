package com.superman.supermarket.service;

import com.superman.supermarket.entity.WholeOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.vo.WholeOrderVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface WholeOrderService extends IService<WholeOrder> {

    /**
     *  添加批发订单,返回受影响的行数和主键
     * @param str
     * @return
     */
    boolean addWholeOrder(String str);
}
