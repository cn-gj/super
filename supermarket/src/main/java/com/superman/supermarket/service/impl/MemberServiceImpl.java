package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.Member;
import com.superman.supermarket.dao.MemberMapper;
import com.superman.supermarket.entity.vo.MemberVo;
import com.superman.supermarket.service.MemberService;
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
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private MemberMapper memberMapper; //注入会员mapper

    @Override
    public List<MemberVo> findAllMember(MemberVo memberVo) {
        return memberMapper.findAllMember(memberVo);
    }

    @Override
    public Integer addMember(Member member,Integer shopId) {
        System.out.println(shopId);
        member.setShopId(shopId);//设置会员所属门店为当前登录的门店编号
        return memberMapper.addMember(member);
    }
    @Override
    public Integer deleteMember(int[] ids) {
        return memberMapper.deleteMember(ids);
    }

    @Override
    public Integer updateMember(Member member) {
        return memberMapper.updateMember(member);
    }

    @Override
    public Integer batchUpdateMemberLevel(int[] ids, Integer levelId) {
        return memberMapper.batchUpdateMemberLevel(ids,levelId);
    }

    @Override
    public Integer batchUpdateSorce(int[] ids, Integer totalSorce) {
        return memberMapper.batchUpdateSorce(ids,totalSorce);
    }
}
