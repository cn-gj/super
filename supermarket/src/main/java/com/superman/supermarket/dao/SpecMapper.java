package com.superman.supermarket.dao;

import com.superman.supermarket.entity.Spec;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface SpecMapper extends BaseMapper<Spec> {

    /**
     * 修改规格
     * @param spec
     * @return
     */
    Integer updateSpec(Spec spec);
}
