package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.Roles;
import com.superman.supermarket.dao.RolesMapper;
import com.superman.supermarket.service.RolesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
@Service
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements RolesService {
    @Resource
    private RolesMapper rolesMapper;
    @Override
    public List<Roles> findAll() {
        return rolesMapper.selectList(null);
    }
}
