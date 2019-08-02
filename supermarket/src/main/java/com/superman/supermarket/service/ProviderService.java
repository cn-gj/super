package com.superman.supermarket.service;

import com.superman.supermarket.entity.Provider;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.vo.ProviderVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface ProviderService extends IService<Provider> {

    /**
     * 查询所有供应商信息
     * @param providerVo
     * @return
     */
    List<ProviderVo> findSelectProvider(ProviderVo providerVo);

    /**
     * 添加供应商
     * @param provider
     * @return
     */
    Integer findInsertProvider(Provider provider,Integer shopId);

    /**
     * 修改供应商
     * @param provider
     * @return
     */
    Integer updateProvider(Provider provider);

    /**
     * 批量删除供应商
     * @param ids
     * @return
     */
    Integer deleteProvider(int [] ids);

    /**
     * 批量修改供应商状态
     * @param ids
     * @return
     */
    Integer updateProStatus(int [] ids,Integer proStatus);

    /**
     * 根据供应商名称查询该门店下的供应商信息
     * @param proName
     * @param shopId
     * @return
     */
    ProviderVo selProviderByProNameAndShopId(String proName,Integer shopId);
}
