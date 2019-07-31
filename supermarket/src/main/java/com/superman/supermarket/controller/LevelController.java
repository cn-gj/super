package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Level;
import com.superman.supermarket.service.LevelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/level")
public class LevelController {
    @Resource
    private LevelService levelService;

    /**
     *编辑会员等级信息
     * @return
     */
    @PostMapping("/updatelevel")
    @ResponseBody
    public String findUpdateLeve(Level level){
        Map<String,Object> map = new HashMap<>();
        Integer count = levelService.findUpdateLeve(level);
        if (count > 0){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     *删除会员等级信息
     * @return
     */
    @PostMapping("/deletelevel")
    @ResponseBody
    public String findDeleteLeve(int id){
        Map<String,Object> map = new HashMap<>();
        Integer count = levelService.findDeleteLeve(id);
        if (count > 0){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     *查询会员等级信息
     * @return
     */
    @PostMapping("/selectlevel")
    @ResponseBody
    public String findSelectLeve(){
        Map<String,Object> map = new HashMap<>();
        List<Level> list = levelService.findSelectLeve();
        map.put("levellist",list);
        return JSON.toJSONString(map);
    }

    /**
     *  根据等级名称查询对象
     * @param levelName
     * @return
     */
    @PostMapping("/findLevelByName")
    @ResponseBody
    public String findLevelByName(String levelName){
        Map<String,Object> map = new HashMap<>();
        if (levelService.findLevelByName(levelName)!= null){
            map.put("state",false);
        }else {
            map.put("state",true);
        }
        return JSON.toJSONString(map);
    }

    /**
     *新增会员等级信息
     * @return
     */
    @PostMapping("/insertlevel")
    @ResponseBody
    public String findInsertLeve(Level level){
        Map<String,Object> map = new HashMap<>();
        Integer count = levelService.findinsertLeve(level);
        if (count > 0){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 根据等级id批量查询会员个数
     * @param ids
     * @return
     */
    @PostMapping("/findMemberCountByIds")
    @ResponseBody
    public String findMemberCountByIds(int[]ids){
        Map<String,Object> map = new HashMap<>();
        long count = levelService.findMemberCountByIds(ids);
        if (count > 0){
            map.put("state",false);
        }else {
            map.put("state",true);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除会员等级
     * @param ids
     * @return
     */
    @PostMapping("/batchLevel")
    @ResponseBody
    public String batchLevel(int[]ids){
        Map<String,Object> map = new HashMap<>();
        int count = levelService.batchLevel(ids);
        if (count == ids.length){
            map.put("state",true);
        }else {
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }
}

