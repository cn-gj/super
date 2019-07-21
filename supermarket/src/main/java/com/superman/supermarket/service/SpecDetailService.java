package com.superman.supermarket.service;

import com.superman.supermarket.entity.SpecDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface SpecDetailService extends IService<SpecDetail> {
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
