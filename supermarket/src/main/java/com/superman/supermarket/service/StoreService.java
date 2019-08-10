package com.superman.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.Store;
import com.superman.supermarket.entity.vo.StoreVo;

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

    /**
     * 查询仓库信息、或多条件查询
     * @param storeVo
     * @return
     */
    List<Store> selStoreAndShop(StoreVo storeVo);

    /**
     * 新增仓库信息
     * @param store
     * @return
     */
    Integer insertStore(Store store);

    /**
     * 删除仓库信息
     * @param storeId
     * @return
     */
    boolean deleteStore(Integer storeId);

    /**
     *  根据仓库id修改仓库名称
     * @param store
     * @return
     */
    int updateStoreNameById(Store store);
}
