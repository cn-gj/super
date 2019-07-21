package com.superman.supermarket.dao;

import com.superman.supermarket.entity.SpecDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface SpecDetailMapper extends BaseMapper<SpecDetail> {

    /**
     * 添加规格详情
     * @param specDetail
     * @return
     */
    Integer addSpecDetail(SpecDetail specDetail);

    /**
     * 删除规格详情
     * @param id
     * @return
     */
    Integer deleteSpecDetail(Integer id);
}
