package com.superman.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.superman.supermarket.entity.Store;
import com.superman.supermarket.dao.StoreMapper;
import com.superman.supermarket.service.StoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Store> findByShopId(Integer shopId) {
        QueryWrapper<Store> wrapper = new QueryWrapper<>();
        wrapper.eq("shop_id",shopId);
        return storeMapper.selectList(wrapper);
    }
}
