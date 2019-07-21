package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.SpecDetail;
import com.superman.supermarket.dao.SpecDetailMapper;
import com.superman.supermarket.service.SpecDetailService;
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
public class SpecDetailServiceImpl extends ServiceImpl<SpecDetailMapper, SpecDetail> implements SpecDetailService {
    @Resource
    private SpecDetailMapper specDetailMapper;

    /**
     * 添加规格详情
     * @param specDetail
     * @return
     */
    @Override
    public Integer addSpecDetail(SpecDetail specDetail) {
        return specDetailMapper.addSpecDetail(specDetail);
    }

    /**
     * 删除规格详情
     * @param id
     * @return
     */
    @Override
    public Integer deleteSpecDetail(Integer id) {
        return specDetailMapper.deleteSpecDetail(id);
    }
}
