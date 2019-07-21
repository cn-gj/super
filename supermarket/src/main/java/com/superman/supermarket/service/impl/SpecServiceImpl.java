package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.Spec;
import com.superman.supermarket.dao.SpecMapper;
import com.superman.supermarket.service.SpecService;
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
public class SpecServiceImpl extends ServiceImpl<SpecMapper, Spec> implements SpecService {
    @Resource
    private SpecMapper specMapper;
    @Override
    public Integer updateSpec(Spec spec) {
        return specMapper.updateSpec(spec);
    }
}
