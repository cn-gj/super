package com.superman.supermarket.service;

import com.superman.supermarket.entity.Roles;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface RolesService extends IService<Roles> {

    /**
     *  查询所有角色
     * @return
     */
    List<Roles> findAll();
}
