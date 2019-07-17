package com.superman.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.superman.supermarket.entity.ShopType;
import com.superman.supermarket.dao.ShopTypeMapper;
import com.superman.supermarket.service.ShopTypeService;
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
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements ShopTypeService {

    @Resource
    private ShopTypeMapper shopTypeMapper;

    @Override
    public List<ShopType> findShopTypeList() {
        return  shopTypeMapper.selectList( new QueryWrapper<ShopType>());
    }
}
