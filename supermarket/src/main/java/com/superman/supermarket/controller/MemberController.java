package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.Member;
import com.superman.supermarket.entity.vo.MemberVo;
import com.superman.supermarket.service.MemberService;
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
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService memberService;

    /**
     * 查询会员信息
     * @return
     */
    @ResponseBody
    @PostMapping("/memberList")
    public String findAllMember(MemberVo memberVo){
        Map<String,Object> map = new HashMap<>();
        List<MemberVo> memberVoList = memberService.findAllMember(memberVo);
        map.put("memberVoList",memberVoList);
        return JSON.toJSONString(map);
    }

    /**
     * 添加会员信息
     * @param member
     * @param spId
     * @return
     */
    @ResponseBody
    @PostMapping("/addmember")
    public String addMember(Member member,Integer spId){
        Map<String,Object> map = new HashMap<>();
        Integer count = memberService.addMember(member,spId);
        if (count > 0){
            map.put("state",true);
        }else{
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除会员
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/delMember")
    public String deleteMember(int [] ids){
        Map<String,Object> map = new HashMap<>();
        Integer count = memberService.deleteMember(ids);
        if (count > 0){
            map.put("state",true);
        }else{
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改会员
     * @param member
     * @return
     */
    @ResponseBody
    @PostMapping("/upMember")
    public String updateMember(Member member){
        Map<String,Object> map = new HashMap<>();
        Integer count = memberService.updateMember(member);
        if (count > 0){
            map.put("state",true);
        }else{
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 批量修改会员所属会员卡
     * @param ids
     * @param levelId
     * @return
     */
    @ResponseBody
    @PostMapping("/batchML")
    public String batchUpdateMemberLevel(int[] ids, Integer levelId){
        Map<String,Object> map = new HashMap<>();
        Integer count = memberService.batchUpdateMemberLevel(ids,levelId);
        if (count > 0){
            map.put("state",true);
        }else{
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }


    /**
     *批量修改会员积分
     * @param ids
     * @param totalSorce
     * @return
     */
    @ResponseBody
        @PostMapping("/batchSorce")
    public String batchUpdateSorce(int[] ids, Integer totalSorce){
        Map<String,Object> map = new HashMap<>();
        Integer count = memberService.batchUpdateSorce(ids, totalSorce);
        if (count > 0){
            map.put("state",true);
        }else{
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }
}

