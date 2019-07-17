package com.superman.supermarket.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superman.supermarket.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.vo.ShopVO;

import java.io.OutputStream;
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
    String findAllShopName();

    /**
     * 多条件查询门店列表信息
     * @param shopVO
     * @return
     */
    List<ShopVO> getShopInfoByCondition(ShopVO shopVO);


    /**
     * 添加门店信息
     * @param shop
     * @return
     */
    Integer addShop(Shop shop);

    /**
     * 根据账号查询门店信息
     * @param account
     * @return
     */
    Shop findShopByAccount(String account);


    /**
     * 根据门店名称查询门店信息
     * @param shopName
     * @return
     */
    Shop getShopInfoByShopName(String shopName);


    /**
     * 修改门店列表信息
     * @param shop
     * @return
     */
    Integer updateShopInfo(Shop shop);

    /**
     * 根据id删除门店信息
     * @param id
     * @return
     */
    Integer delShopInfo(Integer id);


}
