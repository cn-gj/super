package com.superman.supermarket.service.impl;

import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Roles;
import com.superman.supermarket.dao.RolesMapper;
import com.superman.supermarket.service.RolesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String findAll() {
        // 从redis缓存中获取数据
        String rolesListStr = redisTemplate.opsForValue().get("rolesListStr");
        // 判断缓存中是否有数据
        if (rolesListStr == null || rolesListStr.length() == 0){
            // 从数据库中读取数据,存入缓存中
            List<Roles> rolesList =  rolesMapper.findAll();
            rolesListStr = JSON.toJSONString(rolesList);
            redisTemplate.opsForValue().set("rolesListStr",rolesListStr);
        }
        return rolesListStr;
    }
}
