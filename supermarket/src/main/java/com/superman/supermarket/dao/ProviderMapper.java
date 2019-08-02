package com.superman.supermarket.dao;

import com.superman.supermarket.entity.Provider;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.vo.ProviderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface ProviderMapper extends BaseMapper<Provider> {
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
    Integer findInsertProvider(Provider provider);

    /**
     * 根据供应商名称查询该门店下的供应商信息
     * @param proName
     * @param shopId
     * @return
     */
    ProviderVo selProviderByProNameAndShopId(@Param("proName") String proName,@Param("shopId") Integer shopId);
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
    Integer deleteProvider(@Param("ids") int [] ids);

    /**
     * 批量修改供应商状态
     * @param ids
     * @return
     */
    Integer updateProStatus(@Param("ids") int [] ids,@Param("proStatus") Integer proStatus);
}
