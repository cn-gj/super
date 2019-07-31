package com.superman.supermarket.service;

import com.superman.supermarket.entity.Store;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface StoreService extends IService<Store> {

    /**
     *  根据店铺id查询该店铺下的仓库
     * @param shopId
     * @return
     */
    List<Store> findByShopId(Integer shopId);
}
