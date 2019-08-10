package com.superman.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.dao.InventoryDetailMapper;
import com.superman.supermarket.dao.StoreMapper;
import com.superman.supermarket.entity.Store;
import com.superman.supermarket.entity.vo.StoreVo;
import com.superman.supermarket.service.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {
    @Resource
    private StoreMapper storeMapper;
    @Resource
    private InventoryDetailMapper inventoryDetailMapper;

    @Override
    public List<Store> findByShopId(Integer shopId) {
        QueryWrapper<Store> wrapper = new QueryWrapper<>();
        wrapper.eq("shop_id",shopId);
        return storeMapper.selectList(wrapper);
    }

    /**
     * 查询仓库信息、或多条件查询
     * @param storeVo
     * @return
     */
    @Override
    public List<Store> selStoreAndShop(StoreVo storeVo) {
        return storeMapper.selStoreAndShop(storeVo);
    }

    /**
     * 新增仓库信息
     * @param store
     * @return
     */
    @Override
    public Integer insertStore(Store store) {
        return storeMapper.insertStore(store);
    }

    /**
     * 删除仓库信息
     * @param storeId
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean deleteStore(Integer storeId) {
        boolean flag = false;
        Integer invDetailCount = inventoryDetailMapper.selInventoryDetailCount(storeId);
        if (invDetailCount != null && invDetailCount > 0){
            flag = false;
        }else {
            Integer count = storeMapper.deleteStore(storeId);
            if (count > 0){
                flag = true;
            }
        }
        return  flag;
    }

    @Override
    public int updateStoreNameById(Store store) {
        return storeMapper.updateById(store);
    }
}
