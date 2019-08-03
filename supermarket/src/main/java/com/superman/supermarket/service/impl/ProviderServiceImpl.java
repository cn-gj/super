package com.superman.supermarket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.dao.ProShopRelationMapper;
import com.superman.supermarket.dao.ProviderMapper;
import com.superman.supermarket.entity.ProShopRelation;
import com.superman.supermarket.entity.Provider;
import com.superman.supermarket.entity.vo.ProviderVo;
import com.superman.supermarket.service.ProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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
@EnableTransactionManagement
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements ProviderService {

    @Resource
    private ProviderMapper providerMapper;
    @Resource
    private ProShopRelationMapper proShopRelationMapper;

    /**
     * 查询所有供应商
     * @param providerVo
     * @return
     */
    @Override
    public List<ProviderVo> findSelectProvider(ProviderVo providerVo) {
        return providerMapper.findSelectProvider(providerVo);
    }

    /**
     * 根据供应商名称查询该门店下的供应商信息
     * @param proName
     * @param shopId
     * @return
     */
    @Override
    public ProviderVo selProviderByProNameAndShopId(String proName, Integer shopId) {
        return providerMapper.selProviderByProNameAndShopId(proName, shopId);
    }

    /**
     * 添加供应商的同时添加中间表
     * @param provider
     * @param shopId
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer findInsertProvider(Provider provider,Integer shopId) {
        Integer count = providerMapper.findInsertProvider(provider);
        System.out.println(provider.getId());
        ProShopRelation proShopRelation = new ProShopRelation();
        proShopRelation.setProId(provider.getId());
        proShopRelation.setShopId(shopId);
        proShopRelationMapper.addProShop(proShopRelation);
        return count;
    }

    /**
     * 修改供应商
     * @param provider
     * @return
     */
    @Override
    public Integer updateProvider(Provider provider) {
        return providerMapper.updateProvider(provider);
    }

    /**
     * 批量删除供应商
     * @param ids
     * @return
     */
    @Override
    public Integer deleteProvider(int[] ids) {
        return providerMapper.deleteProvider(ids);
    }

    /**
     * 批量修改供应商状态
     * @param ids
     * @return
     */
    @Override
    public Integer updateProStatus(int[] ids,Integer proStatus) {
        return providerMapper.updateProStatus(ids,proStatus);
    }
}
