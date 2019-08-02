package com.superman.supermarket.dao;

import com.superman.supermarket.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.vo.MemberVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 查询所有会员信息
     * @return
     */
    public List<MemberVo> findAllMember(MemberVo memberVo);

    /**
     * 添加会员信息
     * @param member
     * @return
     */
    public Integer addMember(Member member);

    /**
     * 修改会员信息
     * @param member
     * @return
     */
    public Integer updateMember(Member member);

    /**
     * 删除会员
     * @param ids
     * @return
     */
    public Integer deleteMember(@Param("ids") int [] ids);

    /**
     * 批量修改会员所属会员卡
     * @param ids
     * @param levelId
     * @return
     */
    public Integer batchUpdateMemberLevel(@Param("ids") int [] ids,@Param("levelId")Integer levelId);

    /**
     * 修改会员的积分量
     * @param ids
     * @param totalSorce
     * @return
     */
    public Integer batchUpdateSorce(@Param("ids") int [] ids,@Param("totalSorce") Integer totalSorce);

    /**
     * 根据会员手机号（即卡号）查询会员信息
     * @param memberPhone
     * @return
     */
    public Member findMemberByMemberPhone(@Param("memberPhone") String memberPhone,@Param("shopId") Integer shopId);

    /**
     * 根据会员手机号（即卡号）和门店id查询会员信息
     * @param memberPhone
     * @param shopId
     * @return
     */
    public MemberVo selMemberAndLevelByShopIdAndMemberPhone(@Param("memberPhone") String memberPhone,@Param("shopId") Integer shopId);

}
