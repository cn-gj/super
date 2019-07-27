package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.TicketDetail;
import com.superman.supermarket.dao.TicketDetailMapper;
import com.superman.supermarket.entity.vo.TicketDetailVo;
import com.superman.supermarket.service.TicketDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@Service
@Transactional
public class TicketDetailServiceImpl extends ServiceImpl<TicketDetailMapper, TicketDetail> implements TicketDetailService {

    /*@Resource
    private TicketDetailMapper ticketDetailMapper;

    @Override
    public List<TicketDetailVo> findByTicketId(Integer id) {
        return ;
    }*/
}
