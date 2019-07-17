package com.superman.supermarket.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superman.supermarket.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.vo.ShopVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface ShopService extends IService<Shop> {

    /**
     * 查询所有门店信息
     * @return
     */
    List<Shop> findAllShopName();

    /**
     * 多条件查询门店列表信息
     * @param shopVO
     * @return
     */
    List<ShopVO> getShopInfoByCondition(ShopVO shopVO);
}
