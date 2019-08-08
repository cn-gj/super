package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.CheckScope;
import com.superman.supermarket.dao.CheckScopeMapper;
import com.superman.supermarket.service.CheckScopeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class CheckScopeServiceImpl extends ServiceImpl<CheckScopeMapper, CheckScope> implements CheckScopeService {
    @Resource
    private CheckScopeMapper checkScopeMapper;

    @Override
    public List<CheckScope> findAll() {
        return checkScopeMapper.selectList(null);
    }
}
