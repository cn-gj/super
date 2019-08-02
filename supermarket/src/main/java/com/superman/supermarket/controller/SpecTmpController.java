package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.SpecTmp;
import com.superman.supermarket.service.SpecTmpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Controller
@RequestMapping("/specTmp")
public class SpecTmpController {

    @Resource
    private SpecTmpService specTmpService;

    /**
     * 查询规格模板及规格数量 ok
     * @param condition
     * @return
     */
    @ResponseBody
    @PostMapping("/showSpecTmp")
    public String showSpecTmp(String condition){
        Map<String,Object> map = new HashMap<>();
        List<SpecTmp> specTmpList = specTmpService.findSpecTmpAndSpec(condition);
        map.put("specTmpList",specTmpList);
        return JSON.toJSONString(map);
    }

    /**
     *  添加规格模板的同时添加规格 ok
     * @param specTmp
     * @param specStr
     * @return
     */
    @ResponseBody
    @PostMapping("/addStAndSpec")
    public String addSpecTmpAndSpec(SpecTmp specTmp,String specStr){
        Map<String,Object> map = new HashMap<>();
        Integer count = specTmpService.addSpecTmpInfo(specTmp,specStr);
        if (count > 0){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 批量删除specTmp规格模板
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/delSpecTmp")
    public String delSpecTmpInfo(int [] ids){
        Map<String,Object> map = new HashMap<>();
        try {
            Boolean flag = specTmpService.delSpecTmp(ids);
            map.put("state",flag);  // true成功 false 失败
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改规格模板
     * @param specTmp
     * @return
     */
    @ResponseBody
    @PostMapping("/upSpecTmp")
    public String updateSpecTmpInfo(SpecTmp specTmp){
        Map<String,Object> map = new HashMap<>();
        Integer count = specTmpService.updateSpecTmpInfo(specTmp);
        if (count > 0){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 显示规格和规格详情
     * @return
     */
    @ResponseBody
    @PostMapping("/showSpec")
    public String getSpecAndSpecDetailBySpecTmpId(int [] ids){
        Map<String,Object> map = new HashMap<>();
        List<SpecTmp> specTmps = specTmpService.getSpecAndSpecDetailBySpecTmpId(ids);
        map.put("specTmpList",specTmps);
        return JSON.toJSONString(map);
    }

    /**
     * 查询所有规格模板、规格、规格详情
     * @return
     */
    @ResponseBody
    @PostMapping("/findSpecTmpAndSpecAndSd")
    public String selectSpecTmpAndSpecAndSpecDetail(){
        return JSON.toJSONString(specTmpService.selectSpecTmpAndSpecAndSpecDetail());
    }

  /**
     * 查询所有的规格模板、规格、规格详情
     * @return
     */
    @ResponseBody
    @PostMapping("/seSpecTmpAndSpecAndSd")
    public String selectSpecTmpAndSpecAndsd(){
        return JSON.toJSONString(specTmpService.selectSpecTmp());
    }

    /**
     * 根据模板名称查询模板信息 ok
     * @param specTmpName
     * @return
     */
    @ResponseBody
    @PostMapping("/selSpecTmpByName")
    public String selectSpecTmpByName(String specTmpName){
        Map<String,Object> map = new HashMap<>();
        if (specTmpService.selectSpecTmpByName(specTmpName) != null){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }
}

