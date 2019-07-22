package com.superman.supermarket.service;

import com.superman.supermarket.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.vo.MemberVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface MemberService extends IService<Member> {

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
    public Integer addMember(Member member,Integer shopId);

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
}
