package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Roles;
import com.superman.supermarket.service.RolesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Controller
@RequestMapping("/roles")
public class RolesController {

    @Resource
    private RolesService rolesService;
    /**
     *  查询所有角色
     * @return
     */
    @PostMapping("/findAllRoles")
    @ResponseBody
    public String finadAll(){
        List<Roles> rolesList = rolesService.findAll();
        return JSON.toJSONString(rolesList);
    }
}

