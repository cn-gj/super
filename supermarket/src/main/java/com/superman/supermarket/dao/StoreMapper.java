package com.superman.supermarket.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.Store;
import com.superman.supermarket.entity.vo.StoreVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface StoreMapper extends BaseMapper<Store> {
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
    Integer deleteStore(Integer storeId);
}
