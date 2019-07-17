package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.Member;
import com.superman.supermarket.dao.MemberMapper;
import com.superman.supermarket.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
