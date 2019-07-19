package com.superman.supermarket.dao;

import com.superman.supermarket.entity.Roles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface RolesMapper extends BaseMapper<Roles> {
    /**
     *  查询角色id和角色名称列表
     * @return
     */
    List<Roles> findAll();
}
