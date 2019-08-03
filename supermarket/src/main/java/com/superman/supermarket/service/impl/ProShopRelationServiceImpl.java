package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.ProShopRelation;
import com.superman.supermarket.dao.ProShopRelationMapper;
import com.superman.supermarket.service.ProShopRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Service
public class ProShopRelationServiceImpl extends ServiceImpl<ProShopRelationMapper, ProShopRelation> implements ProShopRelationService {
    @Resource
    private ProShopRelationMapper proShopRelationMapper;

    /**
     * 往中间表添加门店和供应商信息
     * @param proShopRelation
     * @return
     */
    @Override
    public Integer addProShop(ProShopRelation proShopRelation) {
        return proShopRelationMapper.addProShop(proShopRelation);
    }
}
