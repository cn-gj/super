package com.superman.supermarket.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superman.supermarket.entity.Shop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.vo.ShopVO;
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
public interface ShopMapper extends BaseMapper<Shop> {

    /**
     * 多条件查询门店列表信息
     * @param shopVO
     * @return
     */
   List<ShopVO> getShopInfoByCondition(@Param("shopVo") ShopVO shopVO);
}
