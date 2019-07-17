package com.superman.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.dao.ShopMapper;
import com.superman.supermarket.entity.vo.ShopVO;
import com.superman.supermarket.service.ShopService;
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
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Resource
    private ShopMapper shopMapper;

    /**
     * 查询所有门店名称
     * @return
     */
    @Override
    public List<Shop> findAllShopName() {
       return  shopMapper.selectList(new QueryWrapper<Shop>());
    }

    /**
     * 根据条件查询门店列表
     * @param shopVO
     * @return
     */
    @Override
    public List<ShopVO> getShopInfoByCondition(ShopVO shopVO) {
        return  shopMapper.getShopInfoByCondition(shopVO);
    }
}
