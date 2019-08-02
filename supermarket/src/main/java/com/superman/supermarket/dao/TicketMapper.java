package com.superman.supermarket.dao;

import com.superman.supermarket.entity.Ticket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.vo.TicketVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface TicketMapper extends BaseMapper<Ticket> {

    /**
     *  根据条件查询收银单信息
     * @param ticketVo
     * @return
     */
    List<TicketVo> findByCondition(TicketVo ticketVo);

    /**
     *  根据id查询收银单信息
     * @param id
     * @return
     */
    TicketVo findById(Integer id);

    int addTicket(Ticket ticket);


}
